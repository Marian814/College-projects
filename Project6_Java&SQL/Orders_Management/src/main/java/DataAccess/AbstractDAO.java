package DataAccess;

import Connection.ConnectionFactory;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    /**
     * Constructor for AbstractDAO class.
     */
    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Create select query for findById.
     * @param field
     * @return
     */
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    /**
     * Create select query for findAll.
     * @return
     */
    private String createSelectAllQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        return sb.toString();
    }

    /**
     * Create objects from ResultSet.
     * @param resultSet
     * @return
     */
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = ctors[0];
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                Class<?>[] paramTypes = ctor.getParameterTypes();
                Object[] params = new Object[paramTypes.length];
                Field[] fields = type.getDeclaredFields();
                for (int i = 0; i < params.length; i++) {
                    String fieldName = fields[i].getName();
                    params[i] = resultSet.getObject(fieldName);
                    Object value = resultSet.getObject(fieldName);
                    if ((paramTypes[i] == double.class || paramTypes[i] == Double.class) && value instanceof java.math.BigDecimal) {
                        value = ((java.math.BigDecimal) value).doubleValue();
                    }
                    if ((paramTypes[i] == int.class || paramTypes[i] == Integer.class) && value instanceof java.math.BigDecimal) {
                        value = ((java.math.BigDecimal) value).intValue();
                    }
                    if ((paramTypes[i] == float.class || paramTypes[i] == Float.class) && value instanceof java.math.BigDecimal) {
                        value = ((java.math.BigDecimal) value).floatValue();
                    }
                    params[i] = value;
                }
                T instance = (T)ctor.newInstance(params);
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Find all objects from a database.
     * @return
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectAllQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO: findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Find an object by id and field from a database.
     * @param id
     * @param field
     * @return
     */
    public T findById(int id, String field) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery(field);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO: findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Insert an object into a database.
     * @param t
     * @return
     */
    public T insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        StringBuilder fields = new StringBuilder();
        StringBuilder placeholders = new StringBuilder();
        Field[] declaredFields = type.getDeclaredFields();
        IntStream.range(0, declaredFields.length).forEach(i -> {
            fields.append(declaredFields[i].getName());
            placeholders.append("?");
            if (i < declaredFields.length - 1) {
                fields.append(", ");
                placeholders.append(", ");
            }
        });
        String query = "INSERT INTO " + type.getSimpleName() +
                " (" + fields + ") VALUES (" + placeholders + ")";
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            for (int i = 0; i < declaredFields.length; i++) {
                declaredFields[i].setAccessible(true);
                Object value = declaredFields[i].get(t);
                statement.setObject(i + 1, value);
            }
            statement.executeUpdate();
            return t;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "DAO: insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Update an object in a database.
     * @param t
     * @return
     */
    public T update(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        Field[] declaredFields = type.getDeclaredFields();
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ").append(type.getSimpleName()).append(" SET ");
        String updateClause = IntStream.range(1, declaredFields.length)
                .mapToObj(i -> declaredFields[i].getName() + " = ?")
                .collect(Collectors.joining(", "));
        sb.append(updateClause);
        sb.append(" WHERE ").append(declaredFields[0].getName()).append(" = ?");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sb.toString());
            for (int i = 1; i < declaredFields.length; i++) {
                declaredFields[i].setAccessible(true);
                statement.setObject(i, declaredFields[i].get(t));
            }
            declaredFields[0].setAccessible(true);
            statement.setObject(declaredFields.length, declaredFields[0].get(t));
            statement.executeUpdate();
            return t;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "DAO: update " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Delete an object from a database.
     * @param t
     * @return
     */
    public T delete(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        Field[] declaredFields = type.getDeclaredFields();
        String keyField = declaredFields[0].getName();
        String query = "DELETE FROM " + type.getSimpleName() + " WHERE " + keyField + " = ?";
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            declaredFields[0].setAccessible(true);
            Object keyValue = declaredFields[0].get(t);
            statement.setObject(1, keyValue);
            statement.executeUpdate();
            return t;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "DAO: delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }
}

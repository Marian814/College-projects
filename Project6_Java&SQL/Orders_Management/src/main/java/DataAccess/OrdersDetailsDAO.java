package DataAccess;

import Model.OrdersDetails;
import Connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrdersDetailsDAO {
    private AbstractDAO<OrdersDetails> ordersDetailsDAO;

    /**
     * Constructor for OrdersDetailsDAO class.
     */
    public OrdersDetailsDAO() {
        ordersDetailsDAO = new AbstractDAO<OrdersDetails>() {};
    }

    /**
     * Find all OrdersDetails from database.
     * @return
     */
    public ArrayList<OrdersDetails> findAllOrdersDetailsDAO() {
        ArrayList<OrdersDetails> list = (ArrayList<OrdersDetails>) ordersDetailsDAO.findAll();
        return list;
    }

    /**
     * Find OrdersDetails by OrderID and ProductID from a database.
     * @param orderId
     * @param productId
     * @return
     */
    public OrdersDetails findByOrderAndProductId(int orderId, int productId) {
        String sql = "SELECT * FROM OrdersDetails WHERE OrderID = ? AND ProductID = ?";
        OrdersDetails ordersDetails = null;
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orderId);
            statement.setInt(2, productId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                ordersDetails = new OrdersDetails(
                        resultSet.getInt("OrderID"),
                        resultSet.getInt("ProductID"),
                        resultSet.getInt("Quantity"),
                        resultSet.getDouble("PriceAtOrderTime")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordersDetails;
    }

    /**
     * Insert OrdersDetails into a database.
     * @param ordersDetails
     * @return
     */
    public OrdersDetails insertOrdersDetailsDAO(OrdersDetails ordersDetails) {
        String sql = "INSERT INTO OrdersDetails (OrderID, ProductID, Quantity, PriceAtOrderTime) VALUES (?, ?, ?, ?)";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, ordersDetails.getOrderID());
            statement.setInt(2, ordersDetails.getProductID());
            statement.setInt(3, ordersDetails.getQuantity());
            statement.setDouble(4, ordersDetails.getPriceAtOrderTime());
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error inserting OrdersDetails: " + e.getMessage());
        }
        return ordersDetails;
    }
}

import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class QueryManager {
    private final Connection connection;

    public QueryManager(Connection connection) {
        this.connection = connection;
    }

    public DefaultTableModel executeQuery(String query) throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            String[] columnNames = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                columnNames[i] = metaData.getColumnName(i + 1);
            }

            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

            while (resultSet.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    row[i] = resultSet.getObject(i + 1);
                }
                model.addRow(row);
            }

            return model;
        }
    }

    public String getQueryByIndex(int index) {
        return switch (index) {
            case 1 -> "SELECT * FROM Factura WHERE total BETWEEN 500 AND 1000 ORDER BY total DESC, data ASC;";
            case 2 -> "SELECT denumire FROM Localitate WHERE LOWER(denumire) NOT LIKE '%a%' ORDER BY denumire ASC;";
            case 3 -> "SELECT c.nume AS nume_client, l.denumire AS localitate FROM Client c JOIN Factura f ON c.id_c = f.id_c JOIN Difuzare d ON f.id_f = d.id_f JOIN Localitate l ON d.id_l = l.id_l WHERE d.datai <= '2024-09-30' AND d.datas >= '2024-09-01';";
            case 4 -> "SELECT DISTINCT d1.id_l AS id_l1, d2.id_l AS id_l2 FROM Difuzare d1 JOIN Difuzare d2 ON d1.datai = d2.datai AND d1.datas = d2.datas AND d1.id_l < d2.id_l;";
            case 5 -> "SELECT c.nume FROM Client c WHERE EXISTS (SELECT 1 FROM Factura f WHERE c.id_c = f.id_c AND f.total = (SELECT MIN(total) FROM Factura WHERE data BETWEEN '2024-04-01' AND '2024-06-30'));";
            case 6 -> "SELECT l.denumire FROM Localitate l WHERE EXISTS (SELECT 1 FROM Difuzare d WHERE l.id_l = d.id_l AND d.datai <= '2024-08-31' AND d.datas >= '2024-08-01');";
            case 7 -> "SELECT d.id_l, d.datai, SUM(f.nr_slideuri) AS total_slideuri FROM Difuzare d JOIN Factura f ON d.id_f = f.id_f WHERE d.datai BETWEEN '2024-10-01' AND '2024-10-31' GROUP BY d.id_l, d.datai ORDER BY d.id_l, d.datai;";
            case 8 -> "SELECT MIN(f.total) AS valoare_minima, AVG(f.total) AS valoare_medie, MAX(f.total) AS valoare_maxima FROM Factura f WHERE f.data BETWEEN '2024-01-01' AND '2024-12-31';";
            default -> null;
        };
    }
}
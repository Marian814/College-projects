import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

public class HomePanel extends JPanel {
    public HomePanel(DatabaseManager dbManager, QueryManager queryManager, JFrame frame) {
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new FlowLayout());
        JLabel label = new JLabel("Client ID: ");
        JTextField clientIdField = new JTextField(10);
        JButton searchButton = new JButton("Search");

        inputPanel.add(label);
        inputPanel.add(clientIdField);
        inputPanel.add(searchButton);

        add(inputPanel, BorderLayout.NORTH);

        JPanel resultPanel = new JPanel(new BorderLayout());
        add(resultPanel, BorderLayout.CENTER);

        searchButton.addActionListener(e -> {
            String clientId = clientIdField.getText().trim();
            if (clientId.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter a Client ID!", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                String query = "SELECT \n" +
                        "    c.id_c,\n" +
                        "    c.nume,\n" +
                        "    c.adresa,\n" +
                        "    f.id_f,\n" +
                        "    f.data,\n" +
                        "    f.nr_slideuri,\n" +
                        "    f.cost_slide,\n" +
                        "    f.nr_zile,\n" +
                        "    f.total,\n" +
                        "    l.id_l,\n" +
                        "    l.denumire,\n" +
                        "    d.datai,\n" +
                        "    d.datas\n" +
                        "FROM \n" +
                        "    Client c\n" +
                        "LEFT JOIN \n" +
                        "    Factura f ON c.id_c = f.id_c\n" +
                        "LEFT JOIN \n" +
                        "    Difuzare d ON f.id_f = d.id_f\n" +
                        "LEFT JOIN \n" +
                        "    Localitate l ON d.id_l = l.id_l\n" +
                        "WHERE \n" +
                        "    c.id_c = " + clientId;
                DefaultTableModel model = queryManager.executeQuery(query);
                JTable table = new JTable(model);
                resultPanel.removeAll();
                resultPanel.add(new JScrollPane(table), BorderLayout.CENTER);
                resultPanel.revalidate();
                resultPanel.repaint();
            }
            catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Query Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
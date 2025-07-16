import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

public class QueryPanel extends JPanel {
    public QueryPanel(QueryManager queryManager, String query) {
        setLayout(new BorderLayout());

        if (query == null) {
            JLabel errorLabel = new JLabel("Invalid Query", JLabel.CENTER);
            add(errorLabel, BorderLayout.CENTER);
            return;
        }

        try {
            DefaultTableModel model = queryManager.executeQuery(query);
            JTable table = new JTable(model);
            add(new JScrollPane(table), BorderLayout.CENTER);
        }
        catch (SQLException e) {
            JLabel errorLabel = new JLabel("Error: " + e.getMessage(), JLabel.CENTER);
            add(errorLabel, BorderLayout.CENTER);
        }
    }
}
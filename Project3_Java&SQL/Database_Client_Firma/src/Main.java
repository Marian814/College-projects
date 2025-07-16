import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost:3306/clienti_firma", "root", "");
            QueryManager queryManager = new QueryManager(dbManager.getConnection());
            MainFrame frame = new MainFrame(dbManager, queryManager);
            frame.setVisible(true);
        });
    }
}
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final CardLayout cardLayout;
    private final JPanel contentPanel;

    public MainFrame(DatabaseManager dbManager, QueryManager queryManager) {
        super("Database Queries");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(null);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBounds(0, 60, 800, 540);

        JButton menuButton = new JButton("Menu");
        menuButton.setFont(new Font("Arial", Font.BOLD, 16));
        menuButton.setBounds(10, 10, 80, 40);

        menuButton.addActionListener(e -> {
            JPopupMenu menuPopup = new JPopupMenu();

            for (int i = 1; i <= 8; i++) {
                JMenuItem item = new JMenuItem("Query " + i);
                int index = i;
                item.addActionListener(ev -> {
                    QueryPanel queryPanel = new QueryPanel(queryManager, queryManager.getQueryByIndex(index));
                    contentPanel.add(queryPanel, "Query" + index);
                    cardLayout.show(contentPanel, "Query" + index);
                });
                menuPopup.add(item);
            }

            JMenuItem homeItem = new JMenuItem("Home");
            homeItem.addActionListener(ev -> cardLayout.show(contentPanel, "Home"));
            menuPopup.add(homeItem);

            menuPopup.show(menuButton, 0, menuButton.getHeight());
        });

        add(menuButton);

        HomePanel homePanel = new HomePanel(dbManager, queryManager, this);
        contentPanel.add(homePanel, "Home");
        add(contentPanel);

        setVisible(true);
    }
}
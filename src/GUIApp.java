// File: GUIApp.java
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GUIApp extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private MenuService menuService;
    private OrderManagerImpl orderManager;

    public GUIApp() {
        menuService = MenuServiceImpl.getInstance();
        orderManager = OrderManagerImpl.getInstance();

        // Ensure data is loaded from files
        menuService.loadMenuItemsFromFile();
        orderManager.loadOrdersFromFile();

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(new MenuDisplayPanel(), "MenuDisplayPanel");
        mainPanel.add(new PendingOrdersPanel(), "PendingOrdersPanel");

        setTitle("Byte Me! Food Ordering System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(mainPanel);

        // Start with the menu display panel
        cardLayout.show(mainPanel, "MenuDisplayPanel");

        setVisible(true);
    }

    private class MenuDisplayPanel extends JPanel {
        public MenuDisplayPanel() {
            setLayout(new BorderLayout());

            JLabel titleLabel = new JLabel("Canteen Menu", JLabel.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            add(titleLabel, BorderLayout.NORTH);

            String[] columnNames = {"Name", "Price", "Type", "Availability", "VIP Exclusive"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            List<MenuItem> menuItems = menuService.getAllItems();

            for (MenuItem item : menuItems) {
                Object[] rowData = {
                        item.getName(),
                        "₹" + item.getPrice(),
                        item.getType(),
                        item.isAvailable() ? "Available" : "Unavailable",
                        item.isVipExclusive() ? "Yes" : "No"
                };
                model.addRow(rowData);
            }

            JTable table = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table);
            add(scrollPane, BorderLayout.CENTER);

            JPanel bottomPanel = new JPanel();
            JButton viewOrdersButton = new JButton("View Pending Orders");
            JButton refreshButton = new JButton("Refresh");
            JButton exitButton = new JButton("Exit");

            viewOrdersButton.addActionListener(e -> cardLayout.show(mainPanel, "PendingOrdersPanel"));
            refreshButton.addActionListener(e -> refreshMenu());
            exitButton.addActionListener(e -> System.exit(0));

            bottomPanel.add(viewOrdersButton);
            bottomPanel.add(refreshButton);
            bottomPanel.add(exitButton);
            add(bottomPanel, BorderLayout.SOUTH);
        }

        private void refreshMenu() {
            menuService.loadMenuItemsFromFile();
            cardLayout.show(mainPanel, "MenuDisplayPanel");
            JOptionPane.showMessageDialog(this, "Menu refreshed.", "Refresh", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private class PendingOrdersPanel extends JPanel {
        public PendingOrdersPanel() {
            setLayout(new BorderLayout());

            JLabel titleLabel = new JLabel("Pending Orders", JLabel.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            add(titleLabel, BorderLayout.NORTH);

            String[] columnNames = {"Order ID", "Customer ID", "Order Time", "Status", "Priority"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            List<Order> pendingOrders = orderManager.getPendingOrders();

            for (Order order : pendingOrders) {
                Object[] rowData = {
                        order.getOrderID(),
                        order.getCustomerID(),
                        order.getOrderTime(),
                        order.getStatus(),
                        order.getPriority()
                };
                model.addRow(rowData);
            }

            JTable table = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table);
            add(scrollPane, BorderLayout.CENTER);

            JPanel bottomPanel = new JPanel();
            JButton backButton = new JButton("Back to Menu");
            JButton refreshButton = new JButton("Refresh");
            JButton exitButton = new JButton("Exit");

            backButton.addActionListener(e -> cardLayout.show(mainPanel, "MenuDisplayPanel"));
            refreshButton.addActionListener(e -> refreshOrders());
            exitButton.addActionListener(e -> System.exit(0));

            bottomPanel.add(backButton);
            bottomPanel.add(refreshButton);
            bottomPanel.add(exitButton);
            add(bottomPanel, BorderLayout.SOUTH);
        }

        private void refreshOrders() {
            orderManager.loadOrdersFromFile();
            cardLayout.show(mainPanel, "PendingOrdersPanel");
            JOptionPane.showMessageDialog(this, "Pending orders refreshed.", "Refresh", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}

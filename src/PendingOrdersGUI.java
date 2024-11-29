import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PendingOrdersGUI extends JFrame {
    private OrderManagerImpl orderManager;

    public PendingOrdersGUI() {
        orderManager = OrderManagerImpl.getInstance();

        // Ensure data is loaded from files
        orderManager.loadOrdersFromFile();

        setTitle("Pending Orders");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();

        setVisible(true);
    }

    private void initComponents() {
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
        JButton refreshButton = new JButton("Refresh");
        JButton closeButton = new JButton("Close");

        refreshButton.addActionListener(e -> refreshOrders());
        closeButton.addActionListener(e -> dispose());

        bottomPanel.add(refreshButton);
        bottomPanel.add(closeButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void refreshOrders() {
        orderManager.loadOrdersFromFile();
        // Refresh logic
        dispose();
        new PendingOrdersGUI();
    }
}

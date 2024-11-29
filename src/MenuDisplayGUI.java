import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MenuDisplayGUI extends JFrame {
    private MenuService menuService;

    public MenuDisplayGUI() {
        menuService = MenuServiceImpl.getInstance();

        // Ensure data is loaded from files
        menuService.loadMenuItemsFromFile();

        setTitle("Canteen Menu");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();

        setVisible(true);
    }

    private void initComponents() {
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
        JButton refreshButton = new JButton("Refresh");
        JButton closeButton = new JButton("Close");

        refreshButton.addActionListener(e -> refreshMenu());
        closeButton.addActionListener(e -> dispose());

        bottomPanel.add(refreshButton);
        bottomPanel.add(closeButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void refreshMenu() {
        menuService.loadMenuItemsFromFile();
        // Refresh logic
        dispose();
        new MenuDisplayGUI();
    }
}

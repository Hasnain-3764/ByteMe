import java.io.Serializable;
import java.util.List;

public class Admin extends User implements Serializable {
    private String adminId;
//    private final CustomerService customerService;
    private final MenuService menuService;
    private OrderManager orderManager;
    private ReportGenerator reportGenerator;

    public Admin(String name, String password, String adminId){
        super(name,password);
        this.adminId = adminId;
//        this.customerService = CustomerService.getInstance();
        this.menuService = MenuServiceImpl.getInstance();
        this.orderManager = OrderManagerImpl.getInstance(); // Initialize orderManager
        this.reportGenerator = new ReportGeneratorImpl();
    }


    @Override
    public String getLoginID() {
        return adminId;
    }

    // new admin specific methods
    public void addMenuItem(MenuItem item){
        boolean isAdded = menuService.addMenuItem(item);
        if(isAdded){
            DisplayUtils.printSuccess("Item '" + item.getName() + "' has been successfully added to the menu.");
        }
        else{
            DisplayUtils.printFailure("Failed to add item '" + item.getName() + "'. It may already exist.");
        }
    }

    public boolean updateMenuItem(MenuItem item)//// changed updateaMenuItem to boolean type
    {
        boolean updated = menuService.updateMenuItem(item);
        DisplayUtils.printSuccess(updated ? "Item updated successfully." : "Item not found.");
        return updated;
    }

    public void removeMenuItem(String itemName) {
        menuService.removeMenuItem(itemName);
        orderManager.denyOrdersWithItem(itemName); // deny orders containing the removed item
//        System.out.println("Item removed successfully.");
    }
    public void trackOrders() {
        System.out.println("Order Tracking! \nStatus: ");
        orderManager.trackOrders();

    }

    public void viewAllUsers(){
        CustomerService customerService = CustomerService.getInstance(); // Lazy initialization
        List<User> users = customerService.getAllUsers();
        DisplayUtils.printHeading("===== Registered Users =====");
        for(User user : users){
            if(user instanceof Admin){
                System.out.println("Admin ID: " + user.getLoginID() + " | Name: " + user.getName());
            }
            else if(user instanceof VIPCustomer){
                System.out.println("VIP ID: " + user.getLoginID() + " | Name: " + user.getName());
            }
            else if(user instanceof RegularCustomer){
                System.out.println("Regular ID: " + user.getLoginID() + " | Name: " + user.getName());
            }
        }
    }


    public void generateSalesReport() {

        System.out.println("Generating Daily Sales Report...");

        reportGenerator.generateDailySalesReport();

    }

}

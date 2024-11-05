public class Admin extends User{
    private String adminId;
    private final CustomerService customerService;
    private final MenuService menuService;
    private OrderManager orderManager;
    private ReportGenerator reportGenerator;

    public Admin(String name, String password, String adminId){
        super(name,password);
        this.adminId = adminId;
        this.customerService = CustomerService.getInstance();
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
            System.out.println("Item '" + item.getName() + "' has been successfully added to the menu.");
        }
        else{
            System.out.println("Failed to add item '" + item.getName() + "'. It may already exist.");
        }
    }

    public boolean updateMenuItem(MenuItem item)//// changed updateaMenuItem to boolean type
    {
        boolean updated = menuService.updateMenuItem(item);
        System.out.println(updated ? "Item updated successfully." : "Item not found.");
        return updated;
    }

    public void removeMenuItem(String itemName) {
        menuService.removeMenuItem(itemName);
        orderManager.denyOrdersWithItem(itemName); // deny orders containing the removed item
        System.out.println("Item removed successfully.");
    }
    public void trackOrders() {
        System.out.println("Order Tracking! \nStatus: ");
        orderManager.trackOrders();

    }

    public void generateSalesReport() {

        System.out.println("Generating Daily Sales Report...");

        reportGenerator.generateDailySalesReport();

    }

}

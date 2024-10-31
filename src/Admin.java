public class Admin extends User{
    private String adminId;
    private MenuService menuService;
    private OrderManager orderManager;
    private ReportGenerator reportGenerator;

    public Admin(String name, String password, String adminId){
        super(name,password);
        this.adminId = adminId;
    }

    public Admin(String name, String password, String adminId, MenuService menuService,
                 ReportGenerator reportGenerator, OrderManager orderManager){
        super(name,password);
        this.adminId = adminId;
        this.menuService = menuService;
        this.reportGenerator = reportGenerator;
        this.orderManager = orderManager;
    }

    @Override
    public String getLoginID() {
        return adminId;
    }

    // new admin specific methods
    public void addMenuItem(MenuItem item){
        menuService.addMenuItem(item);
        System.out.println("Item added successfully.");
    }

    public boolean updateMenuItem(MenuItem item)//// changed updateaMenuItem to boolean type
    {
        boolean updated = menuService.updateMenuItem(item);
        System.out.println(updated ? "Item updated successfully." : "Item not found.");
        return updated;
    }

    public void removeMenuItem(String itemName) {
        menuService.removeMenuItem(itemName);
        System.out.println("Item removed successfully.");
    }
    public void trackOrders() {
        System.out.println("Order Tracking! \nStatus: ");
//        orderManager.trackOrders();

    }

//    public void processOrder(Order order) {
//        orderManager.processOrder(order); // Example method in OrderManager
//    }
//
//    public void generateSalesReport() {
//        reportGenerator.generateDailySalesReport();
//    }

}

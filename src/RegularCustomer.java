import java.util.List;

public class RegularCustomer extends Customer {
//    private MenuService menuService;
//    private OrderManager orderManager;

    public RegularCustomer(String name, String password, String rollNo) {
        super(name, password, rollNo);
    }
//    public RegularCustomer(String name, String password, String rollNo, MenuService menuService, OrderManager orderManager) {
//        super(name, password, rollNo);
//        this.menuService = menuService;
//        this.orderManager = orderManager;
//    }

    public void browseMenu(){
        MenuService menuService = MenuServiceImpl.getInstance();
        System.out.println("Canteen Menu");
        List<MenuItem> items = menuService.getAllItems();
        if(items.isEmpty()) {
            System.out.println("No items available in the menu.");
        }
        else{
            System.out.println("Menu Items.");
            items.forEach(item->System.out.printf(
                    "Name: %s\nPrice: ₹%.2f\nType: %s\nAvailability: %s\n--------------------\\n",
                    item.getName(), item.getPrice(), item.getType(),
                    item.isAvailable() ? "Available":"Unavailable"
            ));
        }
    }
    public void searchMenuItems(String keyword){
        MenuService menuService = MenuServiceImpl.getInstance();
        System.out.println("Searching for "+keyword);
        List<MenuItem> results = menuService.searchItems(keyword);
        if(results.isEmpty()){
            System.out.println("No items found.");
        }
        else{
            results.forEach(result -> System.out.printf("Name: %s\nPrice: ₹%.2f\nType: %s\nAvailability: %s\n--------------------\n",
                    result.getName(), result.getPrice(), result.getType(),
                    result.isAvailable() ? "Available":"Unavailable"));
        }
    }

    @Override
    public void placeOrder(Order order) {
        OrderManager orderManager = OrderManagerImpl.getInstance();
        // Implement order placement logic
        System.out.println("Regular customer order here");
    }

    public void accessRegularBenefits() {
        // Regular customer-specific benefits
    }

    public void viewOrderHistory() {
        OrderManager orderManager = OrderManagerImpl.getInstance();
        System.out.println("Viewing order history:");
    }

}

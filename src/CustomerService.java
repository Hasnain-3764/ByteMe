import java.util.ArrayList;
import java.util.List;

public class CustomerService {

    private static CustomerService instance;
    private final MenuService menuService;
    private final OrderManager orderManager;
    private final AuthenticationManager authenticator;

    public CustomerService() {
        this.menuService = MenuServiceImpl.getInstance();
        this.orderManager = OrderManagerImpl.getInstance();
        this.authenticator = AuthenticationManager.getInstance();

    }

    public static CustomerService getInstance() {
        if (instance == null) {
            instance = new CustomerService();
        }
        return instance;
    }

    public static void resetInstance() {
        instance = null;
    }

    public void browseMenu(){
        System.out.println("Canteen Menu");
        List<MenuItem> items = menuService.getAllItems();
        if(items.isEmpty()) {
            System.out.println("No items available in the menu.");
        }
        else{
//            System.out.println("Menu Items.");
//            items.forEach(item->System.out.printf(
//                    "Name: %s\nPrice: ₹%.2f\nType: %s\nAvailability: %s\n \n--------------------\n \n",
//                    item.getName(), item.getPrice(), item.getType(),
//                    item.isAvailable() ? "Available":"Unavailable"
//            ));
            for (MenuItem item : items) {
//                if (!item.isAvailable()) continue;
                System.out.printf(
                        "Name: %s\nPrice: ₹%.2f\nType: %s\nAvailability: %s\n \n--------------------\n \n",
                    item.getName(), item.getPrice(), item.getType(),
                    item.isAvailable() ? "Available":"Unavailable"
                );
            }
        }
    }

    public void searchMenuItems(String keyword){
        System.out.println("Searching for "+keyword);
        List<MenuItem> results = menuService.searchItems(keyword);
        if(results.isEmpty()){
            System.out.println("No items found.");
        }
        else{
            results.forEach(result -> System.out.printf("Name: %s\nPrice: ₹%.2f\nType: %s\nAvailability: %s\n \n--------------------\n \n",
                    result.getName(), result.getPrice(), result.getType(),
                    result.isAvailable() ? "Available":"Unavailable"));
        }
    }

    // to implement show cart, cancel order, and remove dish from order
    public void placeOrder(Customer customer, Order order) throws DishNotAvailableException{
        if(order==null || order.getItems().isEmpty()){
            System.out.println("Nothing to order. Order creation was cancelled");
            return;
        }
        // check if everything in the order is available
        for(OrderItem orderItem: order.getItems()){
            if(!orderItem.getMenuItem().isAvailable()){
                throw new DishNotAvailableException("Dish " + orderItem.getMenuItem().getName() + " is not available.");
            }
        }
        boolean isVIP = customer instanceof VIPCustomer;
        orderManager.placeOrder(order,isVIP);
        //add order to custormer history
//        customer.addOrderToHistory(order); //to be implemented // no nneed
        DisplayUtils.printSuccess("Order placed successfully!");
    }

    public List<User> getAllUsers(){
        return authenticator.getAllUsers();
    }

    public List<Order> getOrderHistory(String customerID){
        return orderManager.getOrderHistory(customerID);
    }

}

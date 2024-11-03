import java.util.List;

public class CustomerService {

    private static CustomerService instance;
    private final MenuService menuService;
    private final OrderManager orderManager;

    public CustomerService() {
        this.menuService = MenuServiceImpl.getInstance();
        this.orderManager = OrderManagerImpl.getInstance();
    }

    public static CustomerService getInstance() {
        if (instance == null) {
            instance = new CustomerService();
        }
        return instance;
    }
    public void browseMenu(){
        System.out.println("Canteen Menu");
        List<MenuItem> items = menuService.getAllItems();
        if(items.isEmpty()) {
            System.out.println("No items available in the menu.");
        }
        else{
            System.out.println("Menu Items.");
            items.forEach(item->System.out.printf(
                    "Name: %s\nPrice: ₹%.2f\nType: %s\nAvailability: %s\n \n--------------------\n \n",
                    item.getName(), item.getPrice(), item.getType(),
                    item.isAvailable() ? "Available":"Unavailable"
            ));
        }
    }

    public void searchMenuItems(String keyword){
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

    public void placeOrder(Customer customer, Order order) throws DishNotAvailableException{
        if(order==null){
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
        customer.addOrderToHistory(order); //to be implemented
        System.out.println("Order placed successfully");
    }

    public List<Order> getOrderHistory(String customerID){
        return orderManager.getOrderHistory(customerID);
    }

}

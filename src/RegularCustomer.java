import java.util.List;

public class RegularCustomer extends Customer {
    private final CustomerService customerService;

    public RegularCustomer(String name, String password, String rollNo) {
        super(name, password, rollNo);
        this.customerService = CustomerService.getInstance();
    }

    public void browseMenu(){
        customerService.browseMenu();

    }
    public void searchMenuItems(String keyword){
        customerService.searchMenuItems(keyword);
    }

    @Override
    public void placeOrder(Order order) {
        try {
            customerService.placeOrder(this, order);
        } catch (DishNotAvailableException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Placing order.");
    }

    public void accessRegularBenefits() {
        System.out.println("Accessing Regular customer benefits...");
        // Regular customer-specific benefits
    }

//    public void viewOrderHistory() {
//        OrderManager orderManager = OrderManagerImpl.getInstance();
//        System.out.println("Viewing order history:");
//    }

}

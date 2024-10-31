import java.util.List;

public class RegularCustomer extends Customer {
    private final CustomerService customerService;

    public RegularCustomer(String name, String password, String rollNo, CustomerService customerService) {
        super(name, password, rollNo);
        this.customerService = customerService;
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
        // Regular customer-specific benefits
    }

    public void viewOrderHistory() {
        OrderManager orderManager = OrderManagerImpl.getInstance();
        System.out.println("Viewing order history:");
    }

}

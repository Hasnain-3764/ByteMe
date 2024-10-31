import java.util.List;

public class VIPCustomer extends Customer{

    private final CustomerService customerService;

    public VIPCustomer(String name, String password, String rollNo) {
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
        System.out.println("Priority Placing order");
    }


    public void viewOrderHistory() {
        OrderManager orderManager = OrderManagerImpl.getInstance();
        System.out.println("Viewing order history:");
    }

    public void accessVIPBenefits(){
        System.out.println("Accessing VIP benefits...");
    }

}

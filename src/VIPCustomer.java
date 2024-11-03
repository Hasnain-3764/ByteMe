import java.awt.*;
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


//    public void viewOrderHistory() {
//        OrderManager orderManager = OrderManagerImpl.getInstance();
//        System.out.println("Viewing order history:");
//    }

    public void accessVIPBenefits(){
        System.out.println("Accessing VIP benefits...");
        System.out.println("1. View Exclusive Items");
        System.out.println("2. View Current Discounts");
        System.out.println("3. View VIP Statistics");
        System.out.println("4. Back to VIP Menu");
        int choice = InputUtils.readInt("Enter your choice: ", 1, 4);

        switch(choice){
            case 1 -> viewExclusiveMenuItems();
            case 2 -> applyVIPDiscount();
            case 3 -> viewVIPStatistics();
            case 4 -> System.out.println("Returning to VIP Menu...");
            default -> System.out.println("Invalid choice. Try again.");
        }


    }
    // implmement
    public void viewExclusiveMenuItems(){
        System.out.println("VIP Exclusive Menu Items:");
        List<MenuItem> exclusiveItems = MenuServiceImpl.getInstance().filterVipExclusiveItems();
        for(MenuItem item: exclusiveItems){
            System.out.printf("Name: %s | Price: ₹%.2f | Type: %s\n",
                    item.getName(), item.getPrice(), item.getType());
        }
    }
    //implement
    public void applyVIPDiscount(){
        System.out.println("VIP Discounts are automatically applied at checkout.");
    }
    public void viewVIPStatistics(){
        System.out.println("VIP Statistics:");
        System.out.println("Total Orders Placed: ");
        System.out.println("Total Amount Spent: ");
    }

}

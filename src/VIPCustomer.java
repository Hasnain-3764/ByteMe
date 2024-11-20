import org.json.JSONObject;

import java.awt.*;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class VIPCustomer extends Customer implements Serializable {

//    private final CustomerService customerService;

    public VIPCustomer(String name, String password, String rollNo) {
        super(name, password, rollNo);
//        this.customerService = CustomerService.getInstance();
    }

    @Override
    public JSONObject toJSON(){
        JSONObject json = new JSONObject();
        json.put("type", "VIPCustomer");
        json.put("name", this.name);
        json.put("password", this.password);
        json.put("rollNo", this.getLoginID());
        return json;
    }

    public static VIPCustomer fromJSON(JSONObject json){
        String name = json.getString("name");
        String password = json.getString("password");
        String rollNo = json.getString("rollNo");
        return new VIPCustomer(name, password, rollNo);
    }

    public void browseMenu(){
        CustomerService customerService = CustomerService.getInstance();
        customerService.browseMenu();
    }

    public void searchMenuItems(String keyword){
        CustomerService customerService = CustomerService.getInstance();
        customerService.searchMenuItems(keyword);
    }

    @Override
    public void placeOrder(Order order) {
        CustomerService customerService = CustomerService.getInstance();
        try {
            customerService.placeOrder(this, order);
        } catch (DishNotAvailableException e) {
            System.out.println(e.getMessage());
        }
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
        DisplayUtils.printHeading("VIP Exclusive Menu Items:");
        List<MenuItem> exclusiveItems = MenuServiceImpl.getInstance().filterVipExclusiveItems()
                .stream()
                .filter(MenuItem::isAvailable)
                .collect(Collectors.toList());

        if(exclusiveItems.isEmpty()){
            System.out.println("No exclusive items available.");
            return;
        }
        for(int i=0; i < exclusiveItems.size(); i++){
            MenuItem item = exclusiveItems.get(i);
            System.out.printf("%d. Name: %s | Price: ₹%.2f | Type: %s\n",
                    i+1, item.getName(), item.getPrice(), item.getType());
        }
        System.out.println((exclusiveItems.size()+1) + ". Go Back");
        // allowing vip to order from special menu
        int choice = InputUtils.readInt("Select an item to add to cart: ", 1, exclusiveItems.size()+1);
        if(choice == exclusiveItems.size()+1){
            System.out.println("Returning to VIP Menu...");
            return;
        }
        MenuItem selectedItem = exclusiveItems.get(choice -1);
        if(!selectedItem.isAvailable()){
            DisplayUtils.printFailure("Selected item is not available. Can't add to cart.");
            return;
        }
        int quantity = InputUtils.readInt("Enter quantity: ", 1, 100);
        this.getCart().addItem(selectedItem, quantity);
        System.out.println(selectedItem.getName() + " added to cart successfully.");
    }

    //implement
    public void applyVIPDiscount(){
        System.out.println("VIP Discounts are automatically applied at checkout.");
    }
    public void viewVIPStatistics(){
        CustomerService customerService = CustomerService.getInstance();
        List<Order> history = customerService.getOrderHistory(this.getLoginID()); // to be reviewed
        int totalOrders = history.size();
        double totalSpent = 0;
        for(Order order : history){
            totalSpent += order.getTotalPrice();
        }
        System.out.println("===== VIP Statistics =====");
        System.out.println("VIP Statistics:");
        System.out.println("Total Orders Placed: "+totalOrders);
        System.out.println("Total Amount Spent: "+totalSpent);
        System.out.println("==========================");

    }

}

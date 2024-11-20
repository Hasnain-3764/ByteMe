import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

public class RegularCustomer extends Customer implements Serializable {
    private static final long serialVersionUID = 1L;

//    private final CustomerService customerService;

    public RegularCustomer(String name, String password, String rollNo) {
        super(name, password, rollNo);
//        this.customerService = CustomerService.getInstance();
    }

    @Override
    public JSONObject toJSON(){
        JSONObject json = new JSONObject();
        json.put("type", "RegularCustomer");
        json.put("name", this.name);
        json.put("password", this.password);
        json.put("rollNo", this.getLoginID());
        return json;
    }

    public static RegularCustomer fromJSON(JSONObject json){
        String name = json.getString("name");
        String password = json.getString("password");
        String rollNo = json.getString("rollNo");
        return new RegularCustomer(name, password, rollNo);
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
        try {
            CustomerService customerService = CustomerService.getInstance();

            customerService.placeOrder(this, order);
        DisplayUtils.printSuccess("Your order has been placed successfully!");
        } catch (DishNotAvailableException e) {
            System.out.println(e.getMessage());
        }

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

import java.util.ArrayList;
import java.util.List;

public abstract class Customer extends User {
    private String rollNo;
    private List<Order> orderHistory;
    private Cart cart;

    public Customer(String name, String password, String rollNo) {
        super(name, password);
        this.rollNo = rollNo;
        this.orderHistory = new ArrayList<>();
        this.cart = new Cart();
    }

    @Override
    public String getLoginID() {
        return rollNo;  // Returns rollNo as the unique login ID for Customer
    }


    public void addOrderToHistory(Order order){
        orderHistory.add(order);
    }

    public void viewOrderHistory(){
        CustomerService customerService = CustomerService.getInstance();
        List<Order> history = customerService.getOrderHistory(this.getLoginID());
//        if(history.isEmpty()){
//            System.out.println("You have no order history");
//            return;
//        }
//        System.out.println("Your order history: ");
//        for(Order order:history){
//            System.out.println(order);
//        }
        TerminalInterface.displayOrderHistory(history); // use the display function

    }
    public Cart getCart() { // Getter for Cart
        return cart;
    }
    public abstract void placeOrder(Order order);

}

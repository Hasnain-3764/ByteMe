import java.util.ArrayList;
import java.util.List;

public abstract class Customer extends User {
    private String rollNo;
    private List<Order> orderHistory;

    public Customer(String name, String password, String rollNo) {
        super(name, password);
        this.rollNo = rollNo;
        this.orderHistory = new ArrayList<>();
    }

    @Override
    public String getLoginID() {
        return rollNo;  // Returns rollNo as the unique login ID for Customer
    }

//    public void viewOrderHistory() {
//        // Implement viewing order history
//    }

    public void addOrderToHistory(Order order){
        orderHistory.add(order);
    }

//    public abstract void placeOrder(Order order);
}

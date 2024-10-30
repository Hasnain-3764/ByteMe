import java.util.ArrayList;
import java.util.List;

public abstract class Customer extends User{
    private List<Order> orderHistory;
    private String rollNo;
    public Customer(String name, String password, String rollNo){
        super(name,password);
        this.orderHistory = new ArrayList<>();
        this.rollNo = rollNo;
    }

    @Override
    public String getLoginID() {
        return rollNo;
    }

    @Override
    public void viewMenu() {
        System.out.println("Regular Customer Menu");
        // Add menu options specific to Regular Customer
    }

    public void viewOrderHistory(){

    }
    public abstract void placeOrder(Order order);
}

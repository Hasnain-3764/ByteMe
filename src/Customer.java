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
    public boolean login(String rollNo, String password) throws InvalidLoginException {
        if(!this.rollNo.equals(rollNo) || !this.password.equals(password)) {
            throw new InvalidLoginException("Invalid userid or password");
        }
        return true;
    }

    public void viewOrderHistory(){

    }
    public abstract void placeOrder(Order order);
}

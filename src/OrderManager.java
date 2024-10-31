import java.util.List;

public interface OrderManager {
    void placeOrder(Order order, boolean isVIP);
    List<Order> getOrderHistory(String customerID);
    // other methods
}

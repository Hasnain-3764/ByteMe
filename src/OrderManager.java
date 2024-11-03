import java.util.List;

public interface OrderManager {
    void placeOrder(Order order, boolean isVIP);
    List<Order> getOrderHistory(String customerID);
    // other methods
    void trackOrders(); // for admin to track orders
    void updateOrderStatus(String orderID, Order.OrderStatus newStatus);

}

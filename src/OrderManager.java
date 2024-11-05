import java.util.List;

public interface OrderManager {
    void placeOrder(Order order, boolean isVIP);
    List<Order> getOrderHistory(String customerID);
    // other methods
    void trackOrders(); // for admin to track orders
    void updateOrderStatus(int orderID, Order.OrderStatus newStatus) throws OrderNotFoundException;
    void processRefund(int orderID);
    void denyOrdersWithItem(String itemName);

}

import java.util.*;

public class OrderManagerImpl implements OrderManager {
    private static OrderManagerImpl instance;

    private final PriorityQueue<Order> pendingOrders;
    protected Map<String, List<Order>> orderHistories; // as we want it in other classes too

    private OrderManagerImpl() {
        Comparator<Order> orderComparator = Comparator
                .comparing(Order::getPriority)
                .thenComparing(Order::getOrderTime);
        pendingOrders = new PriorityQueue<>(orderComparator);
        orderHistories = new HashMap<>();
    }

    public static OrderManagerImpl getInstance(){
        if(instance == null){
            instance = new OrderManagerImpl();
        }
        return instance;
    }

    @Override
    public void placeOrder(Order order, boolean isVIP){
        System.out.println("Order placed successfully");
    }

    @Override
    public List<Order> getOrderHistory(String customerID) {
        return orderHistories.getOrDefault(customerID, new ArrayList<>());
    }

    @Override
    public void trackOrders() {
        System.out.println("Pending Orders:");
        for (Order order : pendingOrders) {
            System.out.println(order);
        }
    }

    @Override
    public void updateOrderStatus(String orderID, Order.OrderStatus newStatus){

    }



}

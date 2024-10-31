import java.util.*;

public class OrderManagerImpl implements OrderManager {
    private static OrderManagerImpl instance;
    private List<Order> pendingOrders;
    private Map<String, List<Order>> orderHistories;

    private OrderManagerImpl() {
//        pendingOrders = new PriorityQueue<>(new OrderComparator()); // to be implemented
//        orderHistories = new HashMap<>();
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
}

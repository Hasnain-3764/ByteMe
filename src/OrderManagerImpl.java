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
        if(order != null){
            if (isVIP) {
//                order.setPriority(Order.Priority.HIGH);
            }
            pendingOrders.offer(order);
            addToOrderHistory(order);
            System.out.println("Order placed successfully and added to pending orders.");
        }
    }

    private void addToOrderHistory(Order order){
        orderHistories.putIfAbsent(order.getCustomerID(), new ArrayList<>());
        orderHistories.get(order.getCustomerID()).add(order);
    }

    @Override
    public List<Order> getOrderHistory(String customerID) {
        return orderHistories.getOrDefault(customerID, new ArrayList<>());
    }

    @Override
    public void trackOrders() {
        if(pendingOrders.isEmpty()){
            System.out.println("No pending orders.");
            return;
        }
        System.out.println("Pending Orders:");
        for(Order order : pendingOrders){
            System.out.println(order);
        }
    }

    // similar implementation like processRefund
    @Override
    public void updateOrderStatus(String orderID, Order.OrderStatus newStatus){
        Order targetOrder = null; // uninitialised.
        for(Order order:pendingOrders){
            if(order.getOrderID().equals(orderID)){
                targetOrder = order;
                break;
            }
        }
        if(targetOrder != null){
            pendingOrders.remove(targetOrder);
            targetOrder.setStatus(newStatus);
            if(newStatus != Order.OrderStatus.DELIVERED && newStatus!= Order.OrderStatus.DENIED && newStatus != Order.OrderStatus.REFUNDED){
                pendingOrders.offer(targetOrder);
            }
            System.out.println("Order status updated: \n" + targetOrder);

        }
        else{
            System.out.println("OrderID not found in the pending orders.");
        }
    }

    @Override// basically setting statuses
    public void processRefund(String orderID){
        Order order = null; // uninitialised. to be found from history
        for(List<Order> orders : orderHistories.values()){
            for(Order order1: orders){
                if(order1.getOrderID().equals(orderID)) {
                    order = order1;
                    break;
                }
            }
            if(order != null){
                break;
            }
        }
        if(order!=null){
            order.setStatus(Order.OrderStatus.REFUNDED);
            pendingOrders.remove(order);
            System.out.println("Refund processing for orderID: "+orderID);
        }
        else{
            System.out.println("OrderID not found");
        }
    }

    @Override
    public void denyOrdersWithItem(String itemName){
        List<Order> toDeny = new ArrayList<>();
        for(Order order : pendingOrders){
            for(OrderItem orderItem : order.getItems()){
                if(orderItem.getMenuItem().getName().equalsIgnoreCase(itemName)){
                    toDeny.add(order);
                    break;
                }
            }
        }
        for(Order order : toDeny){
            denyOrder(order); // to be implemented
        }
    }

    private void denyOrder(Order order){
        order.setStatus(Order.OrderStatus.DENIED);
        pendingOrders.remove(order);
        System.out.println("Order denied due to item unavailability: \n" + order);
    }
}

// File: OrderManagerImpl.java
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class OrderManagerImpl implements OrderManager {
    private static OrderManagerImpl instance;

    private final PriorityQueue<Order> pendingOrders;
    protected Map<String, List<Order>> orderHistories;
    private int nextOrderID;
    private static final String ORDER_DATA_FILE = "data/orders.json";

    private OrderManagerImpl() {
        Comparator<Order> orderComparator = Comparator
                .comparing(Order::getPriority, Comparator.reverseOrder())
                .thenComparing(Order::getOrderTime);
        pendingOrders = new PriorityQueue<>(orderComparator);
        orderHistories = new HashMap<>();
        nextOrderID = 1;
        loadOrdersFromFile();
    }

    public static OrderManagerImpl getInstance() {
        if (instance == null) {
            instance = new OrderManagerImpl();
        }
        return instance;
    }

    public static void resetInstance() {
        instance = null;
    }

    public void loadOrdersFromFile() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(ORDER_DATA_FILE)));
            JSONArray ordersArray = new JSONArray(content);
            pendingOrders.clear();
            orderHistories.clear();
            for (int i = 0; i < ordersArray.length(); i++) {
                JSONObject orderJSON = ordersArray.getJSONObject(i);
                Order order = Order.fromJSON(orderJSON);
                addToOrderHistory(order);
                if (order.getStatus() != Order.OrderStatus.DELIVERED && order.getStatus() != Order.OrderStatus.DENIED && order.getStatus() != Order.OrderStatus.CANCELED && order.getStatus() != Order.OrderStatus.REFUNDED) {
                    pendingOrders.offer(order);
                }
                if (order.getOrderID() >= nextOrderID) {
                    nextOrderID = order.getOrderID() + 1;
                }
            }
        } catch (IOException e) {
            System.out.println("Order data file not found. Starting with empty order list.");
        }
    }

    public void saveOrdersToFile() {
        JSONArray ordersArray = new JSONArray();
        for (List<Order> orders : orderHistories.values()) {
            for (Order order : orders) {
                ordersArray.put(order.toJSON());
            }
        }
        try (FileWriter file = new FileWriter(ORDER_DATA_FILE)) {
            file.write(ordersArray.toString(4));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void placeOrder(Order order, boolean isVIP) {
        if (order != null) {
            if (isVIP) {
                // Additional logic for VIP if needed
            }
            order.setOrderID(nextOrderID++);
            pendingOrders.offer(order);
            addToOrderHistory(order);
            saveOrdersToFile();
            DisplayUtils.printSuccess("Order received by Admins.");
        }
    }

    public boolean cancelOrder(int orderID, String customerID) {
        Order targetOrder = null;
        for (Order order : pendingOrders) {
            if (order.getOrderID() == (orderID) && order.getCustomerID().equals(customerID)) {
                targetOrder = order;
                break;
            }
        }
        if (targetOrder != null) {
            if (targetOrder.getStatus() == Order.OrderStatus.RECEIVED || targetOrder.getStatus() == Order.OrderStatus.PREPARING) {
                pendingOrders.remove(targetOrder);
                targetOrder.setStatus(Order.OrderStatus.CANCELED);
                saveOrdersToFile();
                DisplayUtils.printSuccess("Order canceled successfully.");
                return true;
            } else {
                DisplayUtils.printFailure("Order cannot be canceled at this stage.");
                return false;
            }
        } else {
            DisplayUtils.printFailure("Order not found or cannot be canceled.");
            return false;
        }
    }

    private void addToOrderHistory(Order order) {
        orderHistories.putIfAbsent(order.getCustomerID(), new ArrayList<>());
        orderHistories.get(order.getCustomerID()).add(order);
    }

    @Override
    public List<Order> getOrderHistory(String customerID) {
        return orderHistories.getOrDefault(customerID, new ArrayList<>());
    }

    @Override
    public void trackOrders() {
        if (pendingOrders.isEmpty()) {
            System.out.println("No pending orders.");
            return;
        }
        DisplayUtils.printHeading("Pending Orders:");
        for (Order order : pendingOrders) {
            System.out.println(order);
        }
    }

    @Override
    public void updateOrderStatus(int orderID, Order.OrderStatus newStatus) throws OrderNotFoundException {
        Order targetOrder = null;
        for (Order order : pendingOrders) {
            if (order.getOrderID() == (orderID)) {
                targetOrder = order;
                break;
            }
        }
        if (targetOrder != null) {
            if (!isValidStatusTransition(targetOrder.getStatus(), newStatus)) {
                DisplayUtils.printFailure("Invalid status transition from " + targetOrder.getStatus() + " to " + newStatus);
                return;
            }
            pendingOrders.remove(targetOrder);
            targetOrder.setStatus(newStatus);
            if (newStatus != Order.OrderStatus.DELIVERED && newStatus != Order.OrderStatus.DENIED && newStatus != Order.OrderStatus.REFUNDED) {
                pendingOrders.offer(targetOrder);
            }
            saveOrdersToFile();
            DisplayUtils.printSuccess("Order status updated: \n" + targetOrder);
        } else {
            DisplayUtils.printFailure("OrderID " + orderID + " not found in pending orders.");
            throw new OrderNotFoundException("OrderID " + orderID + " not found in pending orders.");
        }
    }

    private boolean isValidStatusTransition(Order.OrderStatus currentStatus, Order.OrderStatus newStatus) {
        switch (currentStatus) {
            case RECEIVED:
                return newStatus == Order.OrderStatus.PREPARING || newStatus == Order.OrderStatus.CANCELED;
            case PREPARING:
                return newStatus == Order.OrderStatus.OUT_FOR_DELIVERY || newStatus == Order.OrderStatus.CANCELED;
            case OUT_FOR_DELIVERY:
                return newStatus == Order.OrderStatus.DELIVERED || newStatus == Order.OrderStatus.REFUNDED;
            default:
                return false;
        }
    }

    @Override
    public void processRefund(int orderID) {
        Order order = null;
        for (List<Order> orders : orderHistories.values()) {
            for (Order order1 : orders) {
                if (order1.getOrderID() == (orderID)) {
                    order = order1;
                    break;
                }
            }
            if (order != null) {
                break;
            }
        }
        if (order != null) {
            if (order.getStatus() == Order.OrderStatus.DELIVERED ||
                    order.getStatus() == Order.OrderStatus.CANCELED ||
                    order.getStatus() == Order.OrderStatus.REFUNDED) {
                DisplayUtils.printFailure("Order cannot be refunded as it is already " + order.getStatus());
                return;
            }
            order.setStatus(Order.OrderStatus.REFUNDED);
            pendingOrders.remove(order);
            saveOrdersToFile();
            DisplayUtils.printSuccess("Refund processing for orderID: " + orderID);
        } else {
            DisplayUtils.printFailure("OrderID not found");
        }
    }

    @Override
    public void denyOrdersWithItem(String itemName) {
        List<Order> toDeny = new ArrayList<>();
        for (Order order : pendingOrders) {
            for (OrderItem orderItem : order.getItems()) {
                if (orderItem.getMenuItem().getName().equalsIgnoreCase(itemName)) {
                    toDeny.add(order);
                    break;
                }
            }
        }
        for (Order order : toDeny) {
            denyOrder(order);
        }
        saveOrdersToFile();
    }

    private void denyOrder(Order order) {
        order.setStatus(Order.OrderStatus.DENIED);
        pendingOrders.remove(order);
        DisplayUtils.printFailure("Order denied due to item unavailability: \n" + order);
    }

    public List<Order> getPendingOrders() {
        return new ArrayList<>(pendingOrders);
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> allOrders = new ArrayList<>();
        for (List<Order> orders : orderHistories.values()) {
            allOrders.addAll(orders);
        }
        return allOrders;
    }
}

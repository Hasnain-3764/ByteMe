import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order implements Comparable<Order> {

    public enum Priority {
        HIGH, NORMAL
    }

    public enum OrderStatus{
        RECEIVED, PREPARING, OUT_FOR_DELIVERY, DELIVERED, DENIED, CANCELED, REFUNDED
    }

    private String customerID;
    private Priority priority;
    private final LocalDateTime orderTime;
    private final List<OrderItem> items;
    private OrderStatus status;
//    private String orderID;
    private String specialRequest;
    private static int nextOrderID = 1;
    private int orderID;

    public Order(String customerID, Priority priority, List<OrderItem> items, String specialRequest) {
        this.orderID = nextOrderID++;
        this.customerID = customerID;
        this.priority = priority;
        this.orderTime = LocalDateTime.now();
        this.items = items;
        this.status = OrderStatus.RECEIVED;
        this.specialRequest = specialRequest;

    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("orderID", this.orderID);
        json.put("customerID", this.customerID);
        json.put("priority", this.priority.toString());
        json.put("orderTime", this.orderTime.toString());
        json.put("status", this.status.toString());
        json.put("specialRequest", this.specialRequest);

        JSONArray itemsArray = new JSONArray();
        for (OrderItem item : items) {
//            itemsArray.put(item.toJSON()); // to be implemented
        }
        json.put("items", itemsArray);

        return json;
    }

    public static Order fromJSON(JSONObject json) {
        int orderID = json.getInt("orderID");
        String customerID = json.getString("customerID");
        Priority priority = Priority.valueOf(json.getString("priority"));
        LocalDateTime orderTime = LocalDateTime.parse(json.getString("orderTime"));
        OrderStatus status = OrderStatus.valueOf(json.getString("status"));
        String specialRequest = json.getString("specialRequest");

        JSONArray itemsArray = json.getJSONArray("items");
        List<OrderItem> items = new ArrayList<>();
        for (int i = 0; i < itemsArray.length(); i++) {
            JSONObject itemJSON = itemsArray.getJSONObject(i);
//            OrderItem item = OrderItem.fromJSON(itemJSON); // to be implemented
//            items.add(item);
        }

        Order order = new Order(customerID, priority, items, specialRequest);
        order.setOrderID(orderID);
        order.setStatus(status);
//        order.orderTime = orderTime; // some error here

        return order;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int nextOrderID){
        orderID = nextOrderID;
    }
    // getters and setters
//    public String getOrderID(){
//        return orderID;
//    }
    public OrderStatus getStatus() {
        return status;
    }
    public String getSpecialRequest() {
        return specialRequest;
    }
    public String getCustomerID() {
        return customerID;
    }

    public Priority getPriority() {
        return priority;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public double getTotalPrice(){
        double totalPrice = 0;
        for(OrderItem item : items){
            totalPrice += item.getTotalPrice();
        }
        // discount for VIP
        if(priority == Priority.HIGH){
            totalPrice = totalPrice*0.9; // 10% off
        }
        return totalPrice;
    }

    public void setStatus(OrderStatus newStatus){
        if(isValidStatusTransition(this.status, newStatus)){
            this.status = newStatus;
        }
        else{
            DisplayUtils.printFailure("Invalid status transition from " + this.status + " to " + newStatus);
        }
    }
    private boolean isValidStatusTransition(OrderStatus currentStatus, OrderStatus newStatus){
        switch(currentStatus){
            case RECEIVED:
                return newStatus == OrderStatus.PREPARING || newStatus == OrderStatus.CANCELED;
            case PREPARING:
                return newStatus == OrderStatus.OUT_FOR_DELIVERY || newStatus == OrderStatus.CANCELED;
            case OUT_FOR_DELIVERY:
                return newStatus == OrderStatus.DELIVERED || newStatus == OrderStatus.REFUNDED;
            default:
                return false;
        }
    }
    @Override
    public int compareTo(Order other) {
        int priorityComparison = other.priority.compareTo(this.priority);
        if (priorityComparison != 0) {
            return priorityComparison;
        }
        return this.orderTime.compareTo(other.orderTime);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Order ID: ").append(orderID).append("\n");
        sb.append("Order Time: ").append(orderTime).append("\n");
        sb.append("Priority: ").append(priority).append("\n");
        sb.append("Status: ").append(status).append("\n");
        sb.append("Special Requests: ").append(specialRequest).append("\n");
        sb.append("Items: \n");
        for(OrderItem item: items){
            sb.append("- ").append(item.getMenuItem().getName())
                    .append(" x").append(item.getQuantity())
                    .append(" (₹").append(item.getTotalPrice()).append(")\n");
        }
        sb.append("Total Price: ₹").append(getTotalPrice()).append("\n");
        sb.append("-------------------------");
        return sb.toString();
    }
}

import java.time.LocalDateTime;
import java.util.List;

public class Order implements Comparable<Order> {
    public enum Priority {
        HIGH, NORMAL
    }

    private String customerID;
    private Priority priority;
    private final LocalDateTime orderTime;
    private final List<OrderItem> items;

    public Order(String customerID, Priority priority, List<OrderItem> items) {
        this.customerID = customerID;
        this.priority = priority;
        this.orderTime = LocalDateTime.now();
        this.items = items;
    }
    // getters and setters

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
        sb.append("Order TIme: ").append(orderTime).append("\n");
        sb.append("Priority: ").append(priority).append("\n");
        sb.append("Items: \n");
        for(OrderItem item: items){
            sb.append("- ").append(item.getMenuItem().getName())
                    .append(" x").append(item.getQuantity())
                    .append(" (₹").append(item.getTotalPrice()).append(")\n");
        }
        sb.append("Total Price: ₹").append(getTotalPrice()).append("\n");
        sb.append("-------------------------");
        return toString();
    }

}

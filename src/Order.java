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
        return totalPrice;
    }
    @Override
    public int compareTo(Order other) {
        int priorityComparison = other.priority.compareTo(this.priority);
        if (priorityComparison != 0) {
            return priorityComparison;
        }
        return this.orderTime.compareTo(other.orderTime);    }
}

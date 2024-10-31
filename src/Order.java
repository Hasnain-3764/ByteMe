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
    // other order details...

    @Override
    public int compareTo(Order other) {
        return this.priority.compareTo(other.priority);
    }
}

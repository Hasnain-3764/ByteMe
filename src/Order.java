public class Order implements Comparable<Order> {
    public enum Priority {
        HIGH, NORMAL
    }

    private String customerID;
    private Priority priority;
    // other order details...

    @Override
    public int compareTo(Order other) {
        return this.priority.compareTo(other.priority);
    }
}

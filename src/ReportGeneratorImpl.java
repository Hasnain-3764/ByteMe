import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportGeneratorImpl implements ReportGenerator{

    private final OrderManagerImpl orderManager;

    public ReportGeneratorImpl(){
        this.orderManager = OrderManagerImpl.getInstance();
    }

    @Override
    public void generateDailySalesReport(){
        // Assuming daily report is based on orderTime's date
        LocalDate today = LocalDate.now();
        double totalSales = 0;
        int totalOrders = 0;
        Map<String, Integer> itemPopularity = new HashMap<>();

        for(List<Order> orders : orderManager.orderHistories.values()){
            for(Order order : orders){
                if(order.getOrderTime().toLocalDate().isEqual(today)){
                    if(order.getStatus() == Order.OrderStatus.DELIVERED){
                        totalSales += order.getTotalPrice();
                        totalOrders++;
                        for(OrderItem item : order.getItems()){
                            itemPopularity.put(item.getMenuItem().getName(),
                                    itemPopularity.getOrDefault(item.getMenuItem().getName(), 0) + item.getQuantity());
                        }
                    }
                }
            }
        }
        // finding most popular dishes
        List<Map.Entry<String, Integer>> popularItems = new ArrayList<>(itemPopularity.entrySet());
        popularItems.sort((a, b) -> b.getValue() - a.getValue());

        // Print report
        System.out.println("Daily Sales Report for " + today);
        System.out.println("Total Sales: ₹" + totalSales);
        System.out.println("Total Orders: " + totalOrders);
        System.out.println("Most Popular Items:");
        for(int i=0; i < Math.min(5, popularItems.size()); i++){
            Map.Entry<String, Integer> entry = popularItems.get(i);
            System.out.println((i+1) + ". " + entry.getKey() + " - " + entry.getValue() + " orders");
        }
        System.out.println("-------------------------");
    }
}

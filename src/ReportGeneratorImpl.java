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
    public void generateDailySalesReport() {
        LocalDate today = LocalDate.now();
        double totalSales = 0;
        Map<String, Integer> itemPopularity = new HashMap<>();
        int totalOrder = 0;
        for(List<Order> orders: orderManager.orderHistories.values()){
            for(Order order1:orders){
                if(order1.getStatus() == Order.OrderStatus.DELIVERED){
                    totalSales += order1.getTotalPrice();
                    totalOrder++;
                    for(OrderItem item : order1.getItems()){
                        itemPopularity.put(item.getMenuItem().getName(),
                                itemPopularity.getOrDefault(item.getMenuItem().getName(), item.getQuantity()));
                    }
                }
            }
        }
        List<Map.Entry<String,Integer>> popularItems = new ArrayList<>(itemPopularity.entrySet());
        popularItems.sort((a,b) -> b.getValue() - a.getValue());

        // printing report
        System.out.println("Daily Sales Report for " + today);
        System.out.println("Total Sales: ₹" + totalSales);
        System.out.println("Total Orders: " + totalOrder);
        System.out.println("Most Popular Items:");
        for(int i=0; i < Math.min(5, popularItems.size()); i++){
            Map.Entry<String, Integer> entry = popularItems.get(i);
            System.out.println((i+1) + ". " + entry.getKey() + " - " + entry.getValue() + " orders");
        }
        System.out.println("-------------------------");
    }
}

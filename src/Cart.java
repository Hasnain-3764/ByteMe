import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<OrderItem> items;

    public Cart(){
        items = new ArrayList<>();
    }

    public void addItem(MenuItem menuItem, int quantity){
        for(OrderItem item: items){
            if(item.getMenuItem().getName().equalsIgnoreCase(menuItem.getName())){
                item.setQuantity(item.getQuantity()+quantity);
                return;
            }
        }
        items.add(new OrderItem(menuItem, quantity));
    }

    public void removeItem(String itemName) {
        items.removeIf(item -> item.getMenuItem().getName().equalsIgnoreCase(itemName));
    }

    public void modifyItemQuantity(String itemName, int newQuantity){
        for(OrderItem item: items){
            if(item.getMenuItem().getName().equalsIgnoreCase(itemName)){
                if(newQuantity <= 0){
                    removeItem(itemName);
                }
                else{
                    item.setQuantity(newQuantity);
                }
                break;
            }
        }
    }
}

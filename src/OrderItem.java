import org.json.JSONObject;

public class OrderItem {
    private MenuItem menuItem;
    private int quantity;

    public OrderItem(MenuItem menuItem, int quantity){
        this.menuItem = menuItem;
        this.quantity = quantity;
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("menuItem", menuItem.toJSON());
        json.put("quantity", quantity);
        return json;
    }

    public static OrderItem fromJSON(JSONObject json) {
        MenuItem menuItem = MenuItem.fromJSON(json.getJSONObject("menuItem"));
        int quantity = json.getInt("quantity");
        return new OrderItem(menuItem, quantity);
    }

    public MenuItem getMenuItem(){
        return menuItem;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public double getTotalPrice(){
        return menuItem.getPrice() * quantity;
    }

}

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class MenuItem {
    private String name;
    private double price;
    private String type;
    private boolean availability;
    private boolean vipExclusive;
    private List<Review> reviews;

    public MenuItem(String name, double price, String type, boolean availability, boolean vipExclusive){
        this.name = name;
        this.price = price;
        this.type = type;
        this.availability = availability;
        this.vipExclusive = vipExclusive;
        this.reviews = new ArrayList<>();

    }

    public JSONObject toJSON(){
        JSONObject json = new JSONObject();
        json.put("name", this.name);
        json.put("price", this.price);
        json.put("type", this.type);
        json.put("availability", this.availability);
        json.put("vipExclusive", this.vipExclusive);

        // Serialize reviews
        List<JSONObject> reviewList = new ArrayList<>();
        for(Review review : this.reviews){
//            reviewList.add(review.toJSON()); // to be implemented
        }
        json.put("reviews", reviewList);

        return json;
    }

    public static MenuItem fromJSON(JSONObject json){
        String name = json.getString("name");
        double price = json.getDouble("price");
        String type = json.getString("type");
        boolean availability = json.getBoolean("availability");
        boolean vipExclusive = json.getBoolean("vipExclusive");

        MenuItem item = new MenuItem(name, price, type, availability, vipExclusive);

        // Deserialize reviews
        if(json.has("reviews")){
            for(Object obj : json.getJSONArray("reviews")){
                JSONObject reviewJSON = (JSONObject) obj;
//                Review review = Review.fromJSON(reviewJSON); // to be implemented
//                item.addReview(review);
            }
        }

        return item;
    }


    public boolean isVipExclusive() {
        return vipExclusive;
    }

    public String getName() {
        return name;
    }

//    public void setName(String name) {
//        this.name = name;
//    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    public boolean isAvailable() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public void addReview(Review review){
        reviews.add(review);
    }

    void setVipExclusive(boolean vipExclusive) {
        this.vipExclusive = vipExclusive;
    }

    public List<Review> getReviews(){
        return reviews;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", type='" + type + '\'' +
                ", availability=" + availability +
                ", vipExclusive=" + vipExclusive +
                // ordering unavailabe food, can give in DishNotAvailableException(implement)
                '}';
    }
}

import org.json.JSONObject;

public class Review {
    private String customerID;
    private String itemName;
    private String reviewText;
    private double rating;

    public Review(String customerID, String itemName, String reviewText, double rating){
        this.customerID = customerID;
        this.itemName = itemName;
        this.reviewText = reviewText;
        this.rating = rating;
    }

    public JSONObject toJSON(){
        JSONObject json = new JSONObject();
        json.put("customerID", this.customerID);
        json.put("itemName", this.itemName);
        json.put("reviewText", this.reviewText);
        json.put("rating", this.rating);
        return json;
    }

    public static Review fromJSON(JSONObject json){
        String customerID = json.getString("customerID");
        String itemName = json.getString("itemName");
        String reviewText = json.getString("reviewText");
        int rating = json.getInt("rating");
        return new Review(customerID, itemName, reviewText, rating);
    }

    // getter or setters
    public String getCustomerID(){
        return customerID;
    }
    public String getItemName(){
        return itemName;
    }
    public String getReviewText(){
        return reviewText;
    }
    public double getRating(){
        return rating;
    }
    @Override
    public String toString(){
        return String.format("Customer: %s | Rating: %.1f\n%s\n", customerID, rating, reviewText);
    }
}

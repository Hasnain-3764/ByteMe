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

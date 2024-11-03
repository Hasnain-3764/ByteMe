import java.util.ArrayList;
import java.util.List;

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

    public boolean isVipExclusive() {
        return vipExclusive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
                // ordering unavailabe food, can give in DishNotAvailableException(implement)
                '}';
    }
}

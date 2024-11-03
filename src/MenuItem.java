public class MenuItem {
    private String name;
    private double price;
    private String type;
    private boolean availability;

    public MenuItem(String name, double price, String type, boolean availability){
        this.name = name;
        this.price = price;
        this.type = type;
        this.availability = availability;
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

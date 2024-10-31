import java.util.ArrayList;
import java.util.List;

public class DataInitializer {
    public static List<User> initilizeUsers(){
        List<User> users = new ArrayList<>();
        users.add(new Admin("Admin1","pswd1","A1"));
        users.add(new VIPCustomer("vipUser1", "vipPswd1","2023325"));
        users.add(new RegularCustomer("cust1","custPswd1","2023467"));
        return users;
    }
    public static List<MenuItem> initilizeMenuItems(){
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Pizza", 150,"Meal", true));
        menuItems.add(new MenuItem("Pasta", 120,"Snack", true));
        menuItems.add(new MenuItem("Burger", 80,"Snack", false));
        menuItems.add(new MenuItem("Cola", 50,"Beverage", true));
        menuItems.add(new MenuItem("Salad", 100, "Meal", true));
        menuItems.add(new MenuItem("Ice Cream", 90, "Dessert", true));
        menuItems.add(new MenuItem("Fries", 70, "Snack", false));
        menuItems.add(new MenuItem("Water", 0, "Beverage", true));
        menuItems.add(new MenuItem("Parantha", 250, "Meal", true));
        menuItems.add(new MenuItem("Cheesecake", 150, "Dessert", true));
        menuItems.add(new MenuItem("Soda", 60, "Beverage", false));
        menuItems.add(new MenuItem("Tacos", 130, "Meal", true));
        menuItems.add(new MenuItem("Nachos", 110, "Snack", false));
        menuItems.add(new MenuItem("Lemonade", 40, "Beverage", true));
        menuItems.add(new MenuItem("Butter Chicken", 220, "Meal", true));
        menuItems.add(new MenuItem("Paneer Tikka", 150, "Snack", true));
        menuItems.add(new MenuItem("Biryani", 200, "Meal", true));
        menuItems.add(new MenuItem("Samosa", 50, "Snack", true));
        menuItems.add(new MenuItem("Chai", 30, "Beverage", true));
        menuItems.add(new MenuItem("Naan", 40, "Meal", true));
        menuItems.add(new MenuItem("Raita", 30, "Meal", true));
        menuItems.add(new MenuItem("Daal Makhani", 180, "Meal", true));
        menuItems.add(new MenuItem("Gulab Jamun", 80, "Dessert", true));
        menuItems.add(new MenuItem("Pani Puri", 60, "Snack", true));
        return menuItems;
    }
}

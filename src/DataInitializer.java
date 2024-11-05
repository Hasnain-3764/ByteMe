import java.util.ArrayList;
import java.util.List;

public class DataInitializer {
    public static List<User> initializeUsers(){
        List<User> users = new ArrayList<>();
        users.add(new Admin("Admin1", "Admin@1", "A1"));
        users.add(new Admin("Admin2", "Admin@2", "A2"));

        users.add(new VIPCustomer("VIPUser1", "VIP@1", "V1"));
        users.add(new VIPCustomer("VIPUser2", "VIP@2", "V2"));

        users.add(new RegularCustomer("Regular1", "Reg@1", "R1"));
        users.add(new RegularCustomer("Regular2", "Reg@2", "R2"));

        return users;
    }
    public static List<MenuItem> initilizeMenuItems(){
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Pizza", 150, "Meal", true, true));
        menuItems.add(new MenuItem("Pasta", 120, "Snack", true, true));
        menuItems.add(new MenuItem("Burger", 80, "Snack", true, false));
        menuItems.add(new MenuItem("Cola", 50, "Beverage", true, false));
        menuItems.add(new MenuItem("Salad", 100, "Meal", false, false));
        menuItems.add(new MenuItem("Ice Cream", 90, "Dessert", true, true));
        menuItems.add(new MenuItem("Fries", 70, "Snack", true, false));
        menuItems.add(new MenuItem("Water", 0, "Beverage", true, false));
        menuItems.add(new MenuItem("Parantha", 250, "Meal", true, false));
        menuItems.add(new MenuItem("Cheesecake", 150, "Dessert", true, true));
        menuItems.add(new MenuItem("Soda", 60, "Beverage", true, false));
        menuItems.add(new MenuItem("Tacos", 130, "Meal", true, true));
        menuItems.add(new MenuItem("Nachos", 110, "Snack", true, false));
        menuItems.add(new MenuItem("Lemonade", 40, "Beverage", true, false));
        menuItems.add(new MenuItem("Butter Chicken", 220, "Meal", true, true));
        menuItems.add(new MenuItem("Paneer Tikka", 150, "Snack", true, true));
        menuItems.add(new MenuItem("Biryani", 200, "Meal", true, true));
        menuItems.add(new MenuItem("Samosa", 50, "Snack", true, false));
        menuItems.add(new MenuItem("Chai", 30, "Beverage", true, false));
        menuItems.add(new MenuItem("Naan", 40, "Meal", true, false));
        menuItems.add(new MenuItem("Raita", 30, "Meal", false, false));
        menuItems.add(new MenuItem("Daal Makhani", 180, "Meal", true, false));
        menuItems.add(new MenuItem("Gulab Jamun", 80, "Dessert", true, true));
        menuItems.add(new MenuItem("Pani Puri", 60, "Snack", true, true));
        menuItems.add(new MenuItem("Vegetable Samosa", 50, "Snack", true, false));
        menuItems.add(new MenuItem("Vegetable Sandwich", 80, "Snack", true, false));
        menuItems.add(new MenuItem("Tomato Soup", 60, "Meal", true, false));
        menuItems.add(new MenuItem("Vegetable Biryani", 200, "Meal", true, true));
        menuItems.add(new MenuItem("Masala Dosa", 150, "Meal", false, false));
        menuItems.add(new MenuItem("Paneer Sandwich", 100, "Snack", true, true));
        menuItems.add(new MenuItem("Mixed Vegetable Curry", 180, "Meal", false, false));
        menuItems.add(new MenuItem("Corn on the Cob", 40, "Snack", false, false));
        menuItems.add(new MenuItem("Aaloo Patty", 25, "Snack", true, false));
        menuItems.add(new MenuItem("Fried Rice", 160, "Meal", true, false));
        menuItems.add(new MenuItem("Chickpea Salad", 100, "Snack", false, false));
        menuItems.add(new MenuItem("Cabbage Roll", 130, "Meal", true, false));


        return menuItems;
    }
}

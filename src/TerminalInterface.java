import java.awt.*;
import java.util.Scanner;

public class TerminalInterface {
    private final Scanner scanner;
    private final MenuService menuService;

    public TerminalInterface(MenuService menuService){
        this.menuService = menuService;
        this.scanner = new Scanner(System.in);
    }
    public void showMainMenu(){
        System.out.println("this is main menu");
        System.out.println("1. Admin Login");
        System.out.println("2. VIP Login");
        System.out.println("3. Regular Login");
        System.out.println("4. Exit");

        int choice = InputUtils.readInt("Enter your choice: ", 1, 4);
        switch(choice){
            case 1 -> showAdminMenu();
            case 2 -> showVIPCustomerMenu();
            case 3 -> showRegularCustomerMenu();
            case 4 -> System.out.println("Exiting... Thank You");
            default -> System.out.println("Invalid choice. Try again.");
        }
    }

    // Admin functions
    public void showAdminMenu(){
        while(true){
            System.out.println("\nAdmin Menu:");
            System.out.println("1. View All Menu Items");
            System.out.println("2. Add New Menu Item"); // keep updating unlit 0 is pressed
            System.out.println("3. Update Menu Item");
            System.out.println("4. Remove Menu Item");
            System.out.println("5. Generate Sales Report");
            System.out.println("6, Track Orders");
            System.out.println("7. Logout");

            int choice = InputUtils.readInt("Enter your choice: ", 1, 7);

            switch(choice){
                case 1 -> menuService.getAllItems().forEach(System.out::println);
                case 2 -> addNewItem();
                case 3 -> updateItem();
                case 4 -> removeItem();
//                case 5 -> generateSalesReport(); // to be implemented.
                case 6 -> showOrderTracking();
                case 7 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice, try again");
            }
        }
    }

    // this func will call addMenuItem in menuServices
    private void addNewItem(){
        System.out.println("Enter item name: ");
        String name = scanner.next();
        System.out.println("Enter item price: ");
        double price = scanner.nextDouble();
        System.out.println("Enter item type (e.g. Meal, Snack, Beverages, Dessert): ");
        String type = scanner.next();
        System.out.println("Is the item available? (true/false)");
        boolean availability = scanner.nextBoolean();

        MenuItem item = new MenuItem(name, price, type, availability);
        menuService.addMenuItem(item);
        System.out.println("Item added successfully");
    }

    private void updateItem(){
        System.out.println("Enter the name of the item to update: ");
        String name = scanner.next();
        MenuItem item = menuService.searchItems(name).stream().findFirst().orElse(null);
        if(item == null){
            System.out.println("Item not found.");
            return;
        }
        System.out.print("Enter new price: ");
        double price = scanner.nextDouble();
        System.out.println("Enter new type: ");
        String type = scanner.next();
        System.out.println("Is the item available? (true/false): ");
        boolean availability = scanner.nextBoolean();

        menuService.updateMenuItem(new MenuItem(name,price,type,availability));
        System.out.println("Item updated succesfully");

    }

    private void removeItem(){
        System.out.println("Enter the name of the item to remove: ");
        String name = scanner.next();
        menuService.removeMenuItem(name);
        System.out.println("Item removed successfully.");
    }

    public void showVIPCustomerMenu(){
        while(true){
            System.out.println("\nRegular Customer Menu:");
            System.out.println("1. Browse Menu");
            System.out.println("2. Search Menu Items");
            System.out.println("3. Place an Order");
            System.out.println("4. View Order History");
            System.out.println("5. Logout");

            int choice = InputUtils.readInt("Enter your choice: ", 1, 5);

            switch(choice){

            }
        }
    }

    public void showRegularCustomerMenu(){
        System.out.println("this is customer menu");
    }


    public void showOrderTracking(){
        System.out.println("this is order tracking menu");
    }
    public void handleNavigation(){
        System.out.println("this is navigation handle");
    }

    private void generateSalesReport(){
        System.out.println("Generating sales report...");
    }

}

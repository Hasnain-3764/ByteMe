import java.awt.*;
import java.util.List;
import java.util.Scanner;


public class TerminalInterface {
    private final Scanner scanner;
    private final MenuService menuService;

    public TerminalInterface(MenuService menuService){
        this.menuService = menuService;
        this.scanner = new Scanner(System.in);
    }
//    public void showMainMenu(){
//        System.out.println("this is main menu");
//        System.out.println("1. Admin Login");
//        System.out.println("2. VIP Login");
//        System.out.println("3. Regular Login");
//        System.out.println("4. Exit");
//
//        int choice = InputUtils.readInt("Enter your choice: ", 1, 4);
//        switch(choice){
//            case 1 -> showAdminMenu((Admin) user);
//            case 2 -> showVIPCustomerMenu();
//            case 3 -> showRegularCustomerMenu();
//            case 4 -> System.out.println("Exiting... Thank You");
//            default -> System.out.println("Invalid choice. Try again.");
//        }
//    }

    // Admin functions
    public void showAdminMenu(Admin admin){
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
                case 2 -> admin.addMenuItem(createNewItem()); // to be implemented
                case 3 -> admin.updateMenuItem(chooseItemToUpdate()); // to be implemented
                case 4 -> admin.removeMenuItem(chooseItemToRemove()); // implement..
//                case 5 -> admin.generateSalesReport(); // to be implemented.
                case 6 -> admin.trackOrders();
                case 7 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice, try again");
            }
        }
    }

    // helper methods
    private MenuItem createNewItem(){
        System.out.println("Enter item name: ");
        String name = scanner.nextLine();

        System.out.println("Enter item price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Enter item type: ");
        String type = scanner.nextLine();

        System.out.println("Is it available? (yes/no):");
        String input = scanner.next().trim().toLowerCase();
        boolean availability = input.equals("yes");
        scanner.nextLine();
        return new MenuItem(name, price, type, availability);
    }

    private MenuItem chooseItemToUpdate(){
        System.out.println("Enter item name to update: ");
        String name = scanner.nextLine();
        MenuItem item = menuService.searchItems(name).stream().findFirst().orElse(null);
        if(item == null){
            System.out.println("Item not found!");
            return null;
        }
        System.out.println("Enter new price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();  // consume newline
        System.out.println("Enter new type: ");
        String type = scanner.nextLine();
        System.out.println("Is the item available? (true/false): ");
        boolean availability = scanner.nextBoolean();
        scanner.nextLine();
        return new MenuItem(name,price,type,availability);
    }

    private String chooseItemToRemove() {
        System.out.println("Enter the name of the item to remove: ");
        return scanner.nextLine();
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
                case 1 -> browseMenu();
                case 2 -> searchMenuItems();
                case 3 -> placeOrder();
                case 4 -> viewOrderHistory();
                case 5 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void browseMenu() {
        List<MenuItem> items = menuService.getAllItems();
        if (items.isEmpty()) {
            System.out.println("No items available in the menu.");
        } else {
            System.out.println("Menu Items:");
            items.forEach(item -> System.out.printf("Name: %s\nPrice: ₹%.2f\nType: %s\nAvailability: %s\n--------------------\n",
                    item.getName(), item.getPrice(), item.getType(),
                    item.isAvailable() ? "Available" : "Unavailable"));
        }
    }

    private void searchMenuItems(){
        System.out.println("Enter keyword to search: ");
        String keyword = scanner.next();
        List<MenuItem> results = menuService.searchItems(keyword);
        if(results.isEmpty()){
            System.out.println("No items found.");
        }
        else{
            results.forEach(System.out::println);
        }
    }

    private void placeOrder() {
        System.out.println("Placing order...");
    }

    private void viewOrderHistory(){
        System.out.println("Order History: ");
    }

    public void showRegularCustomerMenu() {
        while (true) {
            System.out.println("\nRegular Customer Menu:");
            System.out.println("1. Browse Menu");
            System.out.println("2. Search Menu Items");
            System.out.println("3. Place an Order");
            System.out.println("4. View Order History");
            System.out.println("5. Logout");

            int choice = InputUtils.readInt("Enter your choice: ", 1, 5);

            switch (choice) {
                case 1 -> browseMenu();
                case 2 -> searchMenuItems();
                case 3 -> placeOrder();
                case 4 -> viewOrderHistory();
                case 5 -> accessVIPBenefits(); // special privelge for our vips
                case 6 -> {
                    System.out.println("Logging out...");
                    return; //exit to main menu
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void accessVIPBenefits() {
        System.out.println("Accessing VIP Benefits...");
    }

    public void showOrderTracking(){
        System.out.println("this is order tracking menu");
    }

    public void handleNavigation() {
        System.out.println("Navigating back to the main menu...");
//        showMainMenu(); //main menu
    }

    private void generateSalesReport(){
        System.out.println("Generating sales report...");
    }

}

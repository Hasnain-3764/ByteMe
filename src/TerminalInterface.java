import java.awt.*;
import java.util.List;
import java.util.Scanner;


public class TerminalInterface {
    private final Scanner scanner;
    private final MenuService menuService;
    private final AuthenticationManager authenticator;  // New field for AuthenticationManager

    public TerminalInterface(MenuService menuService, AuthenticationManager authenticator){
        this.menuService = menuService;
        this.authenticator = authenticator;
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

    public void showVIPCustomerMenu(VIPCustomer vipCustomer){
        while(true){
            System.out.println("\nVIP Customer Menu:");
            System.out.println("1. Browse Menu");
            System.out.println("2. Search Menu Items");
            System.out.println("3. Place an Order");
            System.out.println("4. View Order History");
            System.out.println("5. Access VIP Benefits");

            System.out.println("6. Logout");

            int choice = InputUtils.readInt("Enter your choice: ", 1, 5);

            switch(choice){
                case 1 ->  vipCustomer.browseMenu(); // to be implemented
                case 2 -> {
                    System.out.print("Enter keyword to search: ");
                    String keyword = scanner.next();
                    vipCustomer.searchMenuItems(keyword); // to be implemented
                }
                case 3 -> vipCustomer.placeOrder(new Order());
                case 4 -> vipCustomer.viewOrderHistory();
                case 5 -> vipCustomer.accessVIPBenefits();
                case 6 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    public void showRegularCustomerMenu(RegularCustomer regularCustomer) {
        while (true) {
            System.out.println("\nRegular Customer Menu:");
            System.out.println("1. Browse Menu");
            System.out.println("2. Search Menu Items");
            System.out.println("3. Place an Order");
            System.out.println("4. View Order History");
            System.out.println("5. Become a VIP");
            System.out.println("6. Logout");

            int choice = InputUtils.readInt("Enter your choice: ", 1, 6);

            switch (choice) {
                case 1 -> regularCustomer.browseMenu(); // to be implemented
                case 2 -> {
                    System.out.print("Enter keyword to search: ");
                    String keyword = scanner.next();
                    regularCustomer.searchMenuItems(keyword); // to be implemented
                }
                case 3 -> regularCustomer.placeOrder(new Order());
                case 4 -> regularCustomer.viewOrderHistory();
                case 5 -> becomeVIP(regularCustomer); // special priveledge for our vips
                case 6-> regularCustomer.accessRegularBenefits();
                case 7 -> {
                    System.out.println("Logging out...");
                    return; //exit to main menu
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
    // helper methods

    private void becomeVIP(RegularCustomer regularCustomer){
        System.out.println("To become a VIP, there is one-time upgrade fee.");
        System.out.println("Do you wish to proceed? (yes/no)");
        String response = scanner.next().trim().toLowerCase();

        if(response.equals("yes")){
            VIPCustomer vipCustomer = new VIPCustomer(
                    regularCustomer.getName(),
                    regularCustomer.getPassword(),
                    regularCustomer.getLoginID()
            );
            authenticator.upgradeToVIP(vipCustomer);
            showVIPCustomerMenu(vipCustomer);
            System.out.println("Congratulations! You are now a VIP customer.");
        }
        else{
            System.out.println("Upgrade to VIP cancelled");
        }

    }

    private MenuItem createNewItem(){
        System.out.println("Enter item name: ");
        String name = scanner.nextLine();

        System.out.println("Enter item price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Enter item type: ");
        String type = scanner.nextLine();

        System.out.println("Is it available? (yes/no): ");
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
            System.out.println("Item not found! ");
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

//    private void searchMenuItems(){
//        System.out.println("Enter keyword to search: ");
//        String keyword = scanner.next();
//        List<MenuItem> results = menuService.searchItems(keyword);
//        if(results.isEmpty()){
//            System.out.println("No items found.");
//        }
//        else{
//            results.forEach(result -> System.out.printf("Name: %s\nPrice: ₹%.2f\nType: %s\nAvailability: %s\n--------------------\n",
//                result.getName(), result.getPrice(), result.getType(),
//                createNewItem().isAvailable() ? "Available":"Unavailable"));
//        }
//    }

    public void regularToVip(){

    }

    private void generateSalesReport(){
        System.out.println("Generating sales report...");
    }

}

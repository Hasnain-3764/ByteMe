import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class TerminalInterface {
    private final Scanner scanner;
    private final CustomerService customerService;
    private final MenuService menuService;
    private final AuthenticationManager authenticator;

    public TerminalInterface(){
        this.customerService = CustomerService.getInstance();
        this.menuService = MenuServiceImpl.getInstance();
        this.authenticator = AuthenticationManager.getInstance();
        this.scanner = new Scanner(System.in);
    }

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
            List<MenuItem> items = menuService.getAllItems();
            switch(choice){
                case 1 -> customerService.browseMenu();
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

            int choice = InputUtils.readInt("Enter your choice: ", 1, 6);

            switch(choice){
                case 1 ->  customerService.browseMenu(); // to be implemented
                case 2 -> {
                    System.out.print("Enter keyword to search: ");
                    String keyword = scanner.next();
                    customerService.searchMenuItems(keyword); // to be implemented
                }
                case 3 -> {
                    Order order = createOrder(vipCustomer); // create an Order object
                    try {
                        customerService.placeOrder(vipCustomer, order);
                    } catch (DishNotAvailableException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 4 -> {
                    List<Order> history = customerService.getOrderHistory(vipCustomer.getLoginID());
                    displayOrderHistory(history);
                }
//                case 4 -> vipCustomer.viewOrderHistory();
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
            System.out.println("6. Access Regular Benefits");
            System.out.println("7. Logout");

            int choice = InputUtils.readInt("Enter your choice: ", 1, 7);

            switch (choice) {
                case 1 -> regularCustomer.browseMenu(); // to be implemented
                case 2 -> {
                    System.out.print("Enter keyword to search: ");
                    String keyword = scanner.nextLine();
                    regularCustomer.searchMenuItems(keyword); // to be implemented
                }
                case 3 -> {
                    Order order = createOrder(regularCustomer);
                    try {
                        customerService.placeOrder(regularCustomer, order);
                    } catch (DishNotAvailableException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 4 -> {
                    List<Order> history = customerService.getOrderHistory(regularCustomer.getLoginID());
                    displayOrderHistory(history);
                }
//                case 4 -> regularCustomer.viewOrderHistory();
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
//    private Order createOrder(Customer customer) {
//        // implement order creation logic, e.g., selecting items and quantities
//        // For brevity, assume we return a new Order object
//        return
//    }

    private void becomeVIP(RegularCustomer regularCustomer){
        System.out.println("To become a VIP, there is one-time upgrade fee of ₹1000.");
        System.out.println("Do you wish to proceed? (yes/no)");
        String response = scanner.next().trim().toLowerCase();

        if(response.equals("yes")){
            VIPCustomer vipCustomer = new VIPCustomer(
                    regularCustomer.getName(),
                    regularCustomer.getPassword(),
                    regularCustomer.getLoginID()
            );
            authenticator.upgradeToVIP(vipCustomer);
//            showVIPCustomerMenu(vipCustomer);
            System.out.println("Congratulations! You are now a VIP customer.");
            System.out.println("Please Log out .");
            System.out.println("and log back in as VIP");


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
        System.out.println("Is it available? (yes/no): ");
        String input = scanner.next().trim().toLowerCase();
        boolean availability = input.equals("yes");
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

    // display funciton
    private void displayOrderHistory(List<Order> history){
        if(history == null){
            System.out.println("No orders found in your history");
        }
        else{
            for(Order order:history){
                System.out.println("Order placed on: "+order.getOrderTime());
                System.out.println("Items: ");
                for(OrderItem item: order.getItems()){
                    System.out.println("- "+item.getMenuItem().getName()+ " *" + item.getQuantity() + "(₹" + item.getTotalPrice() + ")");
                }
//                System.out.println("Total price: ₹"+ order.getToalPrice()); // total price to be implemented
                System.out.println("-------------------------");
            }
        }
    }

    private void generateSalesReport(){
        System.out.println("Generating sales report...");
    }

    private Order createOrder(Customer customer){
        List <OrderItem> orderItems = new ArrayList<>();
        while(true){
            System.out.println("Enter the name of item to add to your order (type done to finish): ");
            String itemName = scanner.nextLine().trim();
            if(itemName.equalsIgnoreCase("done")){
                break;
            }
            // checking if it is availabel
            List<MenuItem> search = menuService.searchItems(itemName);
            if(search.isEmpty()){
                System.out.println("Item not found, Please try again.");
                continue;
            }
            MenuItem menuItem = search.get(0); // for now, considering only one item with one name.
            // checking if available
            if (!menuItem.isAvailable()) {
                System.out.println("Sorry, this item is currently unavailable.");
                continue;
            }
            System.out.println("Enter the quantity");
            int quantity = InputUtils.readInt("Quantity: ", 1, 20);

            OrderItem orderItem = new OrderItem(menuItem, quantity);
            orderItems.add(orderItem);
            System.out.println("Item added to list");

        }
        if(orderItems.isEmpty()){
            System.out.println("No items were added to cart");
            return null;
        }
        Order.Priority priority;
        if(customer instanceof VIPCustomer){
            priority = Order.Priority.HIGH;
        }
        else{
            priority = Order.Priority.NORMAL;
        }
        return new Order(customer.getLoginID(),priority,orderItems);
    }





}

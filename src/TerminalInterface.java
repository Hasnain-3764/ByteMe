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
            System.out.println("7. Update Order Status");
            System.out.println("8. Process Refunds");
            System.out.println("9. Logout");

            int choice = InputUtils.readInt("Enter your choice: ", 1, 9);
            List<MenuItem> items = menuService.getAllItems();
            switch(choice){
                case 1 -> customerService.browseMenu();
                case 2 -> admin.addMenuItem(createNewItem()); // to be implemented
                case 3 -> admin.updateMenuItem(chooseItemToUpdate()); // to be implemented
                case 4 -> admin.removeMenuItem(chooseItemToRemove()); // implement..
                case 5 -> admin.generateSalesReport(); // to be implemented.
                case 6 -> admin.trackOrders();
                case 7 -> adminUpdateOrderStatus(admin);
                case 8 -> adminProcessRefund(admin); // to be implemented
                case 9 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice, try again");
            }
        }
    }

    private void adminProcessRefund(Admin admin){
        System.out.println("Enter orderID to process refund:");
        String orderID = scanner.nextLine().trim();
        System.out.println("Order Manager Processing Refund");
        OrderManagerImpl.getInstance().processRefund(orderID);
    }

    private void adminUpdateOrderStatus(Admin admin){
        System.out.println("Enter Order ID to update: ");
        String orderID = scanner.nextLine().trim();
        System.out.println("Choose new status: ");
        System.out.println("1. RECIEVED");
        System.out.println("2. PREPARING");
        System.out.println("3. OUT_FOR_DELIVERY");
        System.out.println("4. DELIVERED");
        System.out.println("5. CANCELED");
        System.out.println("6. REFUNDED");
        int choice = InputUtils.readInt("Enter your choice: ", 1, 6);
        Order.OrderStatus newStatus = switch(choice){

            case 1 -> Order.OrderStatus.RECEIVED;
            case 2 -> Order.OrderStatus.PREPARING;
            case 3 -> Order.OrderStatus.OUT_FOR_DELIVERY;
            case 4 -> Order.OrderStatus.DELIVERED;
            case 5 -> Order.OrderStatus.CANCELED;
            case 6 -> Order.OrderStatus.REFUNDED;
            default -> Order.OrderStatus.RECEIVED; // Default
        };
        OrderManagerImpl.getInstance().updateOrderStatus(orderID, newStatus);

    }

    public void showVIPCustomerMenu(VIPCustomer vipCustomer){
        while(true){
            System.out.println("\nVIP Customer Menu:");
            System.out.println("1. Browse Menu");
            System.out.println("2. Search Menu Items");
            System.out.println("3. Place an Order");
            System.out.println("4. View Order History");
            System.out.println("5. Access VIP Benefits");
            System.out.println("6. Provide a Review");
            System.out.println("7. View Item Reviews");
            System.out.println("8. Logout");

            int choice = InputUtils.readInt("Enter your choice: ", 1, 8);

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
                case 6 -> provideReview(vipCustomer);
                case 7 -> viewItemReviews();
                case 8 -> {
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
            System.out.println("7. Provide a Review");
            System.out.println("8. View Item Reviews");
            System.out.println("9. Logout");

            int choice = InputUtils.readInt("Enter your choice: ", 1, 9);

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
                case 7 -> provideReview(regularCustomer); // to be implemented
                case 8 -> viewItemReviews(); // to be implemented
                case 9 -> {
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
        scanner.nextLine();

        if(response.equals("yes")){
            VIPCustomer vipCustomer = new VIPCustomer(
                    regularCustomer.getName(),
                    regularCustomer.getPassword(),
                    regularCustomer.getLoginID()
            );
            authenticator.upgradeToVIP(vipCustomer);
//            showVIPCustomerMenu(vipCustomer);
            System.out.println("Congratulations! You are now a VIP customer.");
            System.out.println("Please log out and log back in as VIP.");
        }
        else{
            System.out.println("Upgrade to VIP cancelled");
        }

    }

    private MenuItem createNewItem(){
        System.out.println("Enter item name: ");
        String name = scanner.nextLine();

        System.out.println("Enter item price: ");
        double price = readDoubleInput(0, Double.MAX_VALUE);
        scanner.nextLine();

        System.out.print("Enter item category (e.g., Snack, Beverage, Meal, Dessert): ");
        String type = scanner.nextLine().trim();

        System.out.println("Is it available? (yes/no): ");
        boolean availability = readYesNo();

        System.out.print("Is this a VIP exclusive item? (yes/no): ");
        boolean isVipExclusive = readYesNo();

        return new MenuItem(name, price, type, availability, isVipExclusive);
    }

    private int readIntInput(int min, int max){
        int input = -1;
        while(true){
            try{
                input = Integer.parseInt(scanner.nextLine().trim());
                if(input >= min && input <= max){
                    break;
                }
                else{
                    System.out.print("Please enter a number between " + min + " and " + max + ": ");
                }
            }
            catch(NumberFormatException e){
                System.out.print("Invalid input. Please enter a numeric value: ");
            }
        }
        return input;
    }

    private double readDoubleInput(double min, double max){
        double input = -1;
        while(true){
            try{
                input = Double.parseDouble(scanner.nextLine().trim());
                if(input >= min && input <= max){
                    break;
                }
                else{
                    System.out.print("Please enter a value between " + min + " and " + max + ": ");
                }
            }
            catch(NumberFormatException e){
                System.out.print("Invalid input. Please enter a numeric value: ");
            }
        }
        return input;
    }

    private boolean readYesNo(){
        while(true){
            String input = scanner.nextLine().trim().toLowerCase();
            if(input.equals("yes") || input.equals("y")){
                return true;
            }
            else if(input.equals("no") || input.equals("n")){
                return false;
            }
            else{
                System.out.print("Invalid input. Please enter yes or no: ");
            }
        }
    }

    private MenuItem chooseItemToUpdate(){
        System.out.println("Enter item name to update: ");
        String name = scanner.nextLine().trim();
        List<MenuItem> items = menuService.searchItems(name);
        if(items.isEmpty()){
            System.out.println("Item not found! ");
            return null;
        }
        MenuItem existingItem = items.get(0);
        System.out.println("Enter new price (current: ₹" + existingItem.getPrice() + "): ");
        double price = readDoubleInput(0, Double.MAX_VALUE);

        System.out.println("Enter new category (current: " + existingItem.getType() + "): ");
        String type = scanner.nextLine().trim();

        System.out.print("Is it available? (yes/no) (current: " + (existingItem.isAvailable() ? "Yes" : "No") + "): ");
        boolean availability = readYesNo();


        System.out.print("Is this a VIP exclusive item? (yes/no) (current: " + (existingItem.isVipExclusive() ? "Yes" : "No") + "): ");
        boolean isVipExclusive = readYesNo();

        return new MenuItem(name,price,type,availability, isVipExclusive);
    }

    private String chooseItemToRemove() {
        System.out.println("Enter the name of the item to remove: ");
        return scanner.nextLine().trim();
    }


    // display funciton
    public static void displayOrderHistory(List<Order> history){
        if(history == null || history.isEmpty()) {
            System.out.println("No orders found in your history");
        }
        else{
            for(Order order:history){ // to string override
                System.out.println(order);
            }
        }
    }

    private void generateSalesReport(){
        System.out.println("Generating sales report...");
    }

    private Order createOrder(Customer customer){
        List <OrderItem> orderItems = new ArrayList<>();
        System.out.print("Enter any special requests (or press Enter to skip): ");
        String specialRequest = scanner.nextLine().trim();
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
            int quantity = readIntInput(1, 100);

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
        return new Order(customer.getLoginID(),priority,orderItems, specialRequest);
    }

    private void provideReview(Customer customer){
        System.out.println("Enter the name of the item you want to review: ");
        String itemName = scanner.nextLine().trim();
        List<MenuItem> items = menuService.searchItems(itemName);
        if(items.isEmpty()){
            System.out.println("Item not found.");
            return;
        }
        MenuItem item = items.get(0); // assumption: only one item with one name
        System.out.println("Enter your rating (0.0 - 5.0): ");
        double rating = readDoubleInput(0,5);

        System.out.println("Enter your review: ");
        String reviewText = scanner.nextLine().trim();
        Review review = new Review(customer.getLoginID(),item.getName(), reviewText, rating);
        item.addReview(review);
        System.out.println("Reveiw added successfully");


    }

    private void viewItemReviews(){
        System.out.println("Enter the name of the product you want to see reivews");
        String itemName = scanner.nextLine().trim();
        List<MenuItem> items = menuService.searchItems(itemName);
        if(items.isEmpty()){
            System.out.println("No item found with this name.");
            return;
        }
        MenuItem item = items.get(0); // assuming only one item wiht one name
        List<Review> reviews = item.getReviews();
        if(reviews.isEmpty()){
            System.out.println("No reviews for this item so far.");
            return;
        }
        System.out.println("Reviews for "+item.getName()+": ");
        for(Review review: reviews){
            System.out.println(review);
        }
    }



}

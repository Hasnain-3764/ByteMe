import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


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
            DisplayUtils.printHeading("Admin Menu");
            System.out.println("1. View All Menu Items");
            System.out.println("2. Add New Menu Item"); // keep updating unlit 0 is pressed
            System.out.println("3. Update Menu Item");
            System.out.println("4. Remove Menu Item");
            System.out.println("5. Generate Sales Report");
            System.out.println("6, Track Orders");
            System.out.println("7. Update Order Status");
            System.out.println("8. Process Refunds");
            System.out.println("9. View all Users");
            System.out.println("10. Logout");

            int choice = InputUtils.readInt("Enter your choice: ", 1, 10);
            List<MenuItem> items = menuService.getAllItems();
            switch(choice){
                case 1 -> {
                    if(items.isEmpty()){
                        DisplayUtils.printFailure("No menu items available.");
                        DisplayUtils.pause();
                    }
                    else{
                        DisplayUtils.printHeading("===== All Menu Items =====");
                        for(MenuItem item : items){
                            System.out.printf("Name: %s | Price: ₹%.2f | Availability: %s | VIP Exclusive: %s\n",
                                    item.getName(), item.getPrice(),
                                    item.isAvailable() ? "Available" : "Unavailable",
                                    item.isVipExclusive() ? "Yes" : "No");
                        }
                        DisplayUtils.pause();
                    }
                }
                case 2 -> {
                    MenuItem newItem = createNewItem();
                    if(newItem != null){
                        admin.addMenuItem(newItem);
                        DisplayUtils.pause();
                    }
                }
                case 3 -> {
                    MenuItem updatedItem = chooseItemToUpdate();
                    if(updatedItem != null){
                        admin.updateMenuItem(updatedItem);
                        DisplayUtils.pause();
                    }
                }
                case 4 -> {
                    String itemName = chooseItemToRemove();
                    if(itemName != null && !itemName.isEmpty()){
                        admin.removeMenuItem(itemName);
                        DisplayUtils.pause();
                    }
                }
                case 5 -> {
                    admin.generateSalesReport();
                    DisplayUtils.pause();
                }
                case 6 -> {
                    showPendingOrdersGUI();;
                }
                case 7 -> {
                    adminUpdateOrderStatus(admin);
                    DisplayUtils.pause();
                }
                case 8 -> {
                    adminProcessRefund(admin);
                    DisplayUtils.pause();
                }
                case 9 -> {
                    admin.viewAllUsers();
                    DisplayUtils.pause();
                }
                case 10 -> {
                    DisplayUtils.printSuccess("Logging out...");
                    DisplayUtils.pause();
                    return;
                }
                default -> {
                    DisplayUtils.printFailure("Invalid choice, try again.");
                    DisplayUtils.pause();
                }
            }
        }
    }


    private void adminProcessRefund(Admin admin){
        System.out.println("Enter orderID to process refund:");
        int orderID = InputUtils.readInt("Enter the order ID to update: ", 1, Integer.MAX_VALUE);
        System.out.println("Order Manager Processing Refund");
        OrderManagerImpl.getInstance().processRefund(orderID);
    }

    private void adminUpdateOrderStatus(Admin admin){
        while(true){
            System.out.println("Enter Order ID to update (or type 'back' to return): ");
            int orderID = InputUtils.readInt("Enter the order ID to update: ", 1, Integer.MAX_VALUE);
            if(orderID == -1){
                System.out.println("Returning to Admin Menu...");
                return;
            }
            System.out.println("Choose new status: ");
            System.out.println("1. RECEIVED");
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

            try {
                OrderManagerImpl.getInstance().updateOrderStatus(orderID, newStatus);
                DisplayUtils.printSuccess("Order status updated successfully.");
                break; // exit loop upon successful update
            } catch (OrderNotFoundException e) {
                System.out.println(e.getMessage());
                boolean retry = InputUtils.readYesNo("Would you like to try again?");
                if (!retry) {
                    System.out.println("Returning to Admin Menu...");
                    break;
                }
            }


        }
    }


    public void showVIPCustomerMenu(VIPCustomer vipCustomer){
        DisplayUtils.clearConsole();
        while(true){
            DisplayUtils.printBanner();
            System.out.println("\nVIP Customer Menu:");
            System.out.println("1. Browse Menu");
            System.out.println("2. Search Menu Items");
            System.out.println("3. Add Item to Cart");
            System.out.println("4. View Cart");
            System.out.println("5. Modify Cart");
            System.out.println("6. Checkout");
            System.out.println("7. Cancel an Order");
            System.out.println("8. View Order History");
            System.out.println("9. Access VIP Benefits");
            System.out.println("10. Provide a Review");
            System.out.println("11. View Item Reviews");
            System.out.println("12. Sort Menu Items by Price");
            System.out.println("13. Filter Menu Items by Category");
            System.out.println("14. View Pending Orders");
            System.out.println("15. Logout");

            int choice = InputUtils.readInt("Enter your choice: ", 1, 15);

            switch(choice){
                case 1 ->  showMenuGUI();
                case 2 -> {
                    System.out.print("Enter keyword to search: ");
                    String keyword = scanner.nextLine().trim();
                    customerService.searchMenuItems(keyword); // to be implemented
                }
                // to be implemented
                case 3 -> addItemToCart(vipCustomer);
                case 4 -> viewCart(vipCustomer);
                case 5 -> modifyCart(vipCustomer);
                case 6 -> checkOut(vipCustomer);
//                case 3 -> {
//                    Order order = createOrder(vipCustomer); // create an Order object
//                    try {
//                        customerService.placeOrder(vipCustomer, order);
//                    } catch (DishNotAvailableException e) {
//                        System.out.println(e.getMessage());
//                    }
//                }
                case 7 -> cancelOrder(vipCustomer);

                case 8 -> {
                    List<Order> history = customerService.getOrderHistory(vipCustomer.getLoginID());
                    DisplayUtils.printHeading("Your Order History");
                    displayOrderHistory(history, vipCustomer);
                    DisplayUtils.pause();
                }
//                case 4 -> vipCustomer.viewOrderHistory();
                case 9 -> vipCustomer.accessVIPBenefits();
                case 10 -> provideReview(vipCustomer);
                case 11 -> viewItemReviews();
                case 12 -> sortMenuItems(vipCustomer);
                case 13 -> filterMenuItems(vipCustomer);
                case 14 -> viewPendingOrders(vipCustomer);
                case 15 -> {
                    System.out.println("Logging out...");

                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    public void showRegularCustomerMenu(RegularCustomer regularCustomer) {
        DisplayUtils.clearConsole();
        while (true) {
            DisplayUtils.printBanner();
            System.out.println("\nRegular Customer Menu:");
            System.out.println("1. Browse Menu");
            System.out.println("2. Search Menu Items");
            System.out.println("3. Add Item to Cart");
            System.out.println("4. View Cart");
            System.out.println("5. Modify Cart");
            System.out.println("6. Checkout");
            System.out.println("7. Cancel an Order");
            System.out.println("8. View Order History");
            System.out.println("9. Become a VIP");
            System.out.println("10. Access Regular Benefits");
            System.out.println("11. Provide a Review");
            System.out.println("12. View Item Reviews");
            System.out.println("13. Sort Menu Items by Price");
            System.out.println("14. Filter Menu Items by Category");
            System.out.println("15. View Pending Orders"); // New Option
            System.out.println("16. Logout");

            int choice = InputUtils.readInt("Enter your choice: ", 1, 16);

            switch (choice) {
                case 1 -> {
                    regularCustomer.browseMenu(); // to be implemented
                    DisplayUtils.pause();
                }
                case 2 -> {
                    System.out.print("Enter keyword to search: ");
                    String keyword = scanner.nextLine();
                    regularCustomer.searchMenuItems(keyword); // to be implemented
                    DisplayUtils.pause();
                }
                case 3 -> {
                    addItemToCart(regularCustomer);
                    DisplayUtils.pause();
                }
                case 4 -> {
                    viewCart(regularCustomer);
                    DisplayUtils.pause();
                }
                case 5 -> {
                    modifyCart(regularCustomer);
                    DisplayUtils.pause();
                }
                case 6 -> {
                    checkOut(regularCustomer);
                    DisplayUtils.pause();
                }
                case 7 -> {
                    cancelOrder(regularCustomer);
                    DisplayUtils.pause();
                }
                case 8 -> {
                    List<Order> history = customerService.getOrderHistory(regularCustomer.getLoginID());
                    DisplayUtils.printHeading("Your Order History");
                    displayOrderHistory(history, regularCustomer);
                    DisplayUtils.pause();
                }
                case 9 -> {
                    becomeVIP(regularCustomer); // special privilege for our VIPs
                    DisplayUtils.pause();
                }
                case 10 -> {
                    regularCustomer.accessRegularBenefits();
                    DisplayUtils.pause();
                }
                case 11 -> {
                    provideReview(regularCustomer); // to be implemented
                    DisplayUtils.pause();
                }
                case 12 -> {
                    viewItemReviews(); // to be implemented
                    DisplayUtils.pause();
                }
                case 13 -> {
                    sortMenuItems(regularCustomer);
                    DisplayUtils.pause();
                }
                case 14 -> {
                    filterMenuItems(regularCustomer);
                    DisplayUtils.pause();
                }
                case 15 -> {
                    viewPendingOrders(regularCustomer);
                    DisplayUtils.pause();
                }
                case 16 -> {
                    DisplayUtils.printSuccess("Logging out...");
                    DisplayUtils.pause();
                    return; //exit to main menu
                }
                default -> {
                    DisplayUtils.printFailure("Invalid choice. Try again.");
                    DisplayUtils.pause();
                }
            }
        }
    }

    // helper methods
//    private Order createOrder(Customer customer) {
//        // implement order creation logic, e.g., selecting items and quantities
//        // For brevity, assume we return a new Order object
//        return
//    }

    private void viewPendingOrders(Customer customer){
        List<Order> history = customerService.getOrderHistory(customer.getLoginID());
        List<Order> pending = history.stream()
                .filter(order -> order.getStatus() != Order.OrderStatus.DELIVERED &&
                        order.getStatus() != Order.OrderStatus.DENIED &&
                        order.getStatus() != Order.OrderStatus.CANCELED &&
                        order.getStatus() != Order.OrderStatus.REFUNDED)
                .collect(Collectors.toList());

        if(pending.isEmpty()){
            System.out.println("You have no pending orders.");
            return;
        }

        System.out.println("===== Your Pending Orders =====");
        for(int i = 0; i < pending.size(); i++){
            Order order = pending.get(i);
            System.out.printf("%d. Order ID: %s | Status: %s | Total: ₹%.2f | Date: %s\n",
                    i+1, order.getOrderID(), order.getStatus(), order.getTotalPrice(),
                    order.getOrderTime().toLocalDate());
        }
        System.out.println((pending.size()+1) + ". Go Back");

        int choice = InputUtils.readInt("Select an order to view details or go back: ", 1, pending.size()+1);
        if(choice == pending.size()+1){
            return; // Go back
        }
        Order selectedOrder = pending.get(choice-1);
        displayOrderDetails(selectedOrder);
    }

    private void displayOrderDetails(Order order){
        System.out.println("\n===== Order Details =====");
        System.out.println(order);
        System.out.println("=========================\n");
    }


    private void sortMenuItems(Customer customer){
        System.out.println("Sort Menu Items By Price:");
        System.out.println("1. Ascending");
        System.out.println("2. Descending");
        int choice = InputUtils.readInt("Enter your choice: ", 1, 2);
        boolean ascending = choice == 1;
        List<MenuItem> sortedItems = menuService.sortItemsByPrice(ascending);
        if(sortedItems.isEmpty()){
            System.out.println("No items available.");
        } else {
            System.out.println("Sorted Menu Items:");
            for(MenuItem item : sortedItems){
                System.out.printf("Name: %s | Price: ₹%.2f | Type: %s | Availability: %s\n",
                        item.getName(), item.getPrice(), item.getType(),
                        item.isAvailable() ? "Available" : "Unavailable");
            }
        }
    }

    private void filterMenuItems(Customer customer){
        System.out.println("Filter Menu Items By Category:");
        System.out.println("Available Categories: Snack, Beverage, Meal, Dessert");
        System.out.print("Enter category to filter: ");
        String category = scanner.nextLine().trim();
        List<MenuItem> filteredItems = menuService.filterItemsByCategory(category);
        if(filteredItems.isEmpty()){
            System.out.println("No items found for category: " + category);
        }
        else{
            System.out.println("Filtered Menu Items:");
            for(MenuItem item : filteredItems){
                System.out.printf("Name: %s | Price: ₹%.2f | Type: %s | Availability: %s\n",
                        item.getName(), item.getPrice(), item.getType(),
                        item.isAvailable() ? "Available" : "Unavailable");
            }
        }
    }

    private void cancelOrder(Customer customer){
        System.out.print("Enter Order ID to cancel: ");
        int orderID = InputUtils.readInt("Enter the order ID to update: ", 1, Integer.MAX_VALUE);
        boolean canceled = OrderManagerImpl.getInstance().cancelOrder(orderID, customer.getLoginID());
        if(canceled){
            System.out.println("Your order has been canceled successfully.");
        }
        else{
            System.out.println("Unable to cancel the order. It may have already been processed.");
        }
    }

    private void addItemToCart(Customer customer){
        System.out.println("Enter the name of the item to add to your cart: ");
        String itemName = scanner.nextLine().trim();
        List<MenuItem> searchResults = menuService.searchItems(itemName);
        if(searchResults.isEmpty()){
            System.out.println("No items found matching \"" + itemName + "\". Please try again.");
            return;
        }
        MenuItem menuItem = searchResults.get(0);
        if(!menuItem.isAvailable()){
            DisplayUtils.printFailure("Item is not available. Can't add to cart.");
            return;
        }
        int quantity = InputUtils.readInt("Enter the quantity: ", 1, 100);
        customer.getCart().addItem(menuItem, quantity);
        DisplayUtils.printSuccess(menuItem.getName() + " added to cart successfully.");
        DisplayUtils.pause();
        DisplayUtils.clearConsole();
    }


    private void viewCart(Customer customer){
        Cart cart = customer.getCart(); // every customer has a unique cart
        if(cart.isEmpty()){
            DisplayUtils.printFailure("Your Cart is Empty.");
            return;
        }
        System.out.println("Your Cart: ");
        for(OrderItem item: cart.getItems()){
            System.out.println(item.getMenuItem().getName() + " X" +
            item.getQuantity() + " = ₹" + item.getTotalPrice());
        }
        System.out.println("Total Price: ₹"+cart.getTotalPrice());
    }

    private void modifyCart(Customer customer){
        Cart cart = customer.getCart();
        if(cart.isEmpty()){
            DisplayUtils.printFailure("Your cart is empty.");
            return;
        }
        System.out.println("Modify Cart: ");

        System.out.println("1. Change item quantity");
        System.out.println("2. Remove Item");
        System.out.println("3. Go Back");
        int choice = InputUtils.readInt("Enter your choice: ", 1,3);
        scanner.nextLine();
        switch (choice){
            case 1 -> {
                System.out.println("Enter the name of the item to modify: ");
                String itemName = scanner.nextLine().trim();
                System.out.println("Enter the new quantity: ");
                int newQuantity = InputUtils.readInt("Enter the new quantity: ", 0, 100);
                cart.modifyItemQuantity(itemName,newQuantity);
                DisplayUtils.printSuccess("Item quantity updated.");
            }
            case 2 -> {
                System.out.println("Enter the name of the item to remove: ");
                String removeItemName = scanner.nextLine().trim();
                boolean confirm = InputUtils.readYesNo("Are you sure you want to remove '" + removeItemName + "' from your cart?");

                if(confirm){
                    cart.removeItem(removeItemName);
                    DisplayUtils.printSuccess("Item removed from cart.");
                }
                else{
                    DisplayUtils.printInfo("Item removal canceled.");
                }
            }
            case 3 -> System.out.println("Returning to customer menu.");
            default -> DisplayUtils.printFailure("Invalid choice");
        }
    }

    private void checkOut(Customer customer){
        Cart cart = customer.getCart();
        if(cart.isEmpty()){
            DisplayUtils.printFailure("Your cart is empty.");
            return;
        }
        DisplayUtils.printSuccess("Proceeding to checkout...");
        viewCart(customer);
        System.out.println("Enter any special requests (or press Enter to skip): ");
        String specialRequests = scanner.nextLine().trim();
        System.out.println("Enter delivery address: ");
        String deliveryAddress = scanner.nextLine().trim();
        List<OrderItem> orderItems = new ArrayList<>(cart.getItems());
        Order.Priority priority = (customer instanceof VIPCustomer) ? Order.Priority.HIGH : Order.Priority.NORMAL;
        Order order = new Order(customer.getLoginID(), priority,orderItems,specialRequests + " | Delivery Address: "+deliveryAddress);

        customer.placeOrder(order);
        cart.clear();
        DisplayUtils.printSuccess("Cart checkout successful.");
        displayReceipt(order);
        DisplayUtils.pause();
    }

    private void displayReceipt(Order order){
        DisplayUtils.printHeading("\n===== Receipt =====");
        System.out.println("Order ID: " + order.getOrderID());
        System.out.println("Order Time: " + order.getOrderTime());
        System.out.println("Status: " + order.getStatus());
        System.out.println("Special Requests: " + order.getSpecialRequest());
        System.out.println("\nItems:");
        for(OrderItem item : order.getItems()){
            System.out.printf("%s x%d = ₹%.2f\n",
                    item.getMenuItem().getName(),
                    item.getQuantity(),
                    item.getTotalPrice());
        }
        double subtotal = 0;
        for(OrderItem item : order.getItems()){
            subtotal += item.getTotalPrice();
        }
        System.out.printf("Subtotal: ₹%.2f\n", subtotal);
        if(order.getPriority() == Order.Priority.HIGH){
            double discount = subtotal * 0.10;
            System.out.printf("VIP Discount (10%%): -₹%.2f\n", discount);
        }
        System.out.printf("Total: ₹%.2f\n", order.getTotalPrice());
        System.out.println("===================\n");
    }


    private void becomeVIP(RegularCustomer regularCustomer){
        System.out.println("To become a VIP, there is one-time upgrade fee of ₹1000.");
        boolean confirm = InputUtils.readYesNo("Do you wish to proceed with the upgrade?");
;

        if(confirm){
            boolean paymentSuccess = processPayment(regularCustomer, 1000);
            if(paymentSuccess){
                VIPCustomer vipCustomer = new VIPCustomer(
                        regularCustomer.getName(),
                        regularCustomer.getPassword(),
                        regularCustomer.getLoginID()
                );
                authenticator.upgradeToVIP(vipCustomer);
    //            showVIPCustomerMenu(vipCustomer);
                DisplayUtils.printSuccess("Congratulations! You are now a VIP customer.");
                System.out.println("Log out and please log back in again as VIP.");
                return;
            }
            else{
                DisplayUtils.printFailure("Payment failed. VIP upgrade was not completed.");
            }
        }
        else{
            DisplayUtils.printFailure("Upgrade to VIP cancelled");
        }

    }

    private boolean processPayment(Customer customer, double amount){
        // assuming payment is successful always.
        DisplayUtils.printInfo("Processing payment of ₹" + amount + "...");
        DisplayUtils.pause();
        DisplayUtils.printSuccess("Payment successful.");
        return true;
    }

    private MenuItem createNewItem(){
        System.out.println("Enter item name: ");
        String name = scanner.nextLine();

        System.out.println("Enter item price: ");
        double price = readDoubleInput(0, Double.MAX_VALUE);

        System.out.print("Enter item category (e.g., Snack, Beverage, Meal, Dessert): ");
        String type = scanner.nextLine().trim();

        System.out.println("Is it available? (yes/no): ");
        boolean availability = readYesNo();

        System.out.print("Is this a VIP exclusive item? (yes/no): ");
        boolean isVipExclusive = readYesNo();

        return new MenuItem(name, price, type, availability, isVipExclusive);
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
                DisplayUtils.printFailure("Invalid input. Please enter a numeric value: ");
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
               DisplayUtils.printFailure("Invalid input. Please enter yes or no: ");
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
    public static void displayOrderHistory(List<Order> history, Customer customer){
        if(history == null || history.isEmpty()) {
            System.out.println("No orders found in your history");
        }
        while(true){
            DisplayUtils.printHeading("===== Your Order History =====");

//            for(Order order:history){ // to string override
//                System.out.println(order);
//            }

            for(int i = 0; i < history.size(); i++){
                Order order = history.get(i);
                System.out.printf("%d. Order ID: %s | Status: %s | Total: ₹%.2f | Date: %s\n",
                        i+1, order.getOrderID(), order.getStatus(), order.getTotalPrice(),
                        order.getOrderTime().toLocalDate());
            }
            System.out.println((history.size()+1) + ". Re-order a Previous Order");
            System.out.println((history.size()+2) + ". Go Back");
            int choice = InputUtils.readInt("Select an order to re-order or go back: ", 1, history.size()+2);
            if(choice == history.size()+1){
                selectOrderToReOrder(history, customer);
            }
            else if(choice == history.size()+2){
                return; // Go back
            }
            else{
                DisplayUtils.printFailure("Invalid choice. Returning to Order History.");
            }
        }
    }

    private static void selectOrderToReOrder(List<Order> history, Customer customer){
        int orderChoice = InputUtils.readInt("Enter the number of the order to re-order or type 0 to go back: ", 0, history.size());
        if(orderChoice == 0){
            return;
        }
        if(orderChoice < 1 || orderChoice > history.size()){
            DisplayUtils.printFailure("Invalid choice. Returning to Order History.");
            return;
        }
        Order selectedOrder = history.get(orderChoice-1);
        reOrder(selectedOrder, customer);
    }

    private static void reOrder(Order order, Customer customer){
        List<OrderItem> unavailableItems = new ArrayList<>();
        List<OrderItem> availableItems = new ArrayList<>();

        for(OrderItem item : order.getItems()){
            if(!item.getMenuItem().isAvailable()){
                unavailableItems.add(item);
            }
            else{
                availableItems.add(item);
            }
        }

        if(!unavailableItems.isEmpty()){
            System.out.println("The following items are no longer available and will not be re-ordered:");
            for(OrderItem item : unavailableItems){
                System.out.println("- " + item.getMenuItem().getName());
            }
        }

        if(!availableItems.isEmpty()){
            for(OrderItem item : availableItems){
                customer.getCart().addItem(item.getMenuItem(), item.getQuantity());
            }
            DisplayUtils.printSuccess("Available items from Order ID " + order.getOrderID() + " have been added to your cart.");
        }

        if(unavailableItems.isEmpty()){
            DisplayUtils.printSuccess("All items have been successfully re-ordered.");
        }
        else if(!availableItems.isEmpty()){
            DisplayUtils.printInfo("Some items were unavailable and have been excluded from your re-order.");
        }
        else{
            DisplayUtils.printFailure("Re-ordering failed as none of the items are available.");
        }
    }



    // not using anymore(after implementing cart)
//    private Order createOrder(Customer customer){
//        List <OrderItem> orderItems = new ArrayList<>();
//        System.out.print("Enter any special requests (or press Enter to skip): ");
//        String specialRequest = scanner.nextLine().trim();
//        while(true){
//            System.out.println("Enter the name of item to add to your order (type done to finish): ");
//            String itemName = scanner.nextLine().trim();
//            if(itemName.equalsIgnoreCase("done")){
//                break;
//            }
//            // checking if it is availabel
//            List<MenuItem> search = menuService.searchItems(itemName);
//            if(search.isEmpty()){
//                System.out.println("Item not found, Please try again.");
//                continue;
//            }
//            MenuItem menuItem = search.get(0); // for now, considering only one item with one name.
//            // checking if available
//            if (!menuItem.isAvailable()) {
//                System.out.println("Sorry, this item is currently unavailable.");
//                continue;
//            }
//            System.out.println("Enter the quantity");
//            int quantity = readIntInput(1, 100);
//
//            OrderItem orderItem = new OrderItem(menuItem, quantity);
//            orderItems.add(orderItem);
//            System.out.println("Item added to list");
//
//        }
//        if(orderItems.isEmpty()){
//            System.out.println("No items were added to cart");
//            return null;
//        }
//        Order.Priority priority;
//        if(customer instanceof VIPCustomer){
//            priority = Order.Priority.HIGH;
//        }
//        else{
//            priority = Order.Priority.NORMAL;
//        }
//        return new Order(customer.getLoginID(),priority,orderItems, specialRequest);
//    }

    // new createOrder method to align with cart.
    private Order createOrder(Customer customer){
        Cart cart = customer.getCart();
        if(cart.isEmpty()){
            DisplayUtils.printFailure("Your cart is empty. Add items before placing an order.");
            return null;
        }

        System.out.println("Enter any special requests (or press Enter to skip): ");
        String specialRequests = scanner.nextLine().trim();

        System.out.println("Enter delivery address: ");
        String deliveryAddress = scanner.nextLine().trim();

        List<OrderItem> orderItems = new ArrayList<>(cart.getItems());

        Order.Priority priority = (customer instanceof VIPCustomer) ? Order.Priority.HIGH : Order.Priority.NORMAL;
        String combinedSpecialRequest = specialRequests.isEmpty() ? "Delivery Address: " + deliveryAddress
                : specialRequests + " | Delivery Address: " + deliveryAddress;

        return new Order(customer.getLoginID(), priority, orderItems, combinedSpecialRequest);
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

        // check if the customer has ordered this item before allowing a review
        List<Order> orders = customerService.getOrderHistory(customer.getLoginID());
        boolean hasOrdered = orders.stream()
                .flatMap(order -> order.getItems().stream())
                .anyMatch(orderItem -> orderItem.getMenuItem().getName().equalsIgnoreCase(itemName));

        if(!hasOrdered){
            DisplayUtils.printFailure("You can only review items you have ordered.");
            return;
        }

        Review review = new Review(customer.getLoginID(), item.getName(), reviewText, rating);
        item.addReview(review);
        DisplayUtils.printSuccess("Review added successfully.");


    }

    private void viewItemReviews(){
        System.out.println("Enter the name of the product you want to see reivews");
        String itemName = scanner.nextLine().trim();
        List<MenuItem> items = menuService.searchItems(itemName);
        if(items.isEmpty()){
            DisplayUtils.printFailure("No item found with this name.");
            return;
        }
        MenuItem item = items.get(0); // assuming only one item wiht one name
        List<Review> reviews = item.getReviews();
        if(reviews.isEmpty()){
            DisplayUtils.printFailure("No reviews for this item so far.");
            return;
        }
        System.out.println("Reviews for "+item.getName()+": ");
        for(Review review: reviews){
            System.out.println(review);
        }
    }

    public void showMenuGUI() {
        SwingUtilities.invokeLater(() -> {
            new MenuDisplayGUI();
        });
    }

    public void showPendingOrdersGUI() {
        SwingUtilities.invokeLater(() -> {
            new PendingOrdersGUI();
        });
    }


}


import java.util.Scanner;

public class Main {
    private static AuthenticationManager authenticator = AuthenticationManager.getInstance();
    private static Scanner scanner = new Scanner(System.in);
    private static final MenuService menuService = MenuServiceImpl.getInstance();
    private static TerminalInterface terminalInterface = new TerminalInterface();

    public static void main(String[] args) {
        DisplayUtils.clearConsole();
        DisplayUtils.printBanner();
        while (true) {
            System.out.println("Welcome to IIIT-D Canteen");
            System.out.println("1. Login");
            System.out.println("2. SignUp");
            System.out.println("3. Exit the Application");
            int choice = InputUtils.readInt("Enter your choice: ", 1, 3);
            switch (choice) {
                case 1 -> handleLogin();
                case 2 -> handleSignup();
                case 3 -> {
                    System.out.println("Exiting the application. Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }
    private static void handleLogin() {
        while (true) {
            DisplayUtils.printHeading("Login Menu");
            System.out.println("Login as: ");
            System.out.println("1. Admin login");
            System.out.println("2. VIP login");
            System.out.println("3. Regular login");
            System.out.println("4. <-- Go Back");

            int choice = InputUtils.readInt("Enter your choice: ", 1, 4);
            if (choice == 4) {
                System.out.println("Returning to main menu...");
                DisplayUtils.clearConsole();
                DisplayUtils.printBanner();
                return;
            }

            switch (choice) {
                case 1 -> adminLogin(); // for printing only. the logic is same for all users
                case 2 -> vipLogin();
                case 3 -> regularLogin();
                default -> {
                    DisplayUtils.printFailure("Invalid choice, please try again.");
                    DisplayUtils.pause();
                    continue;
                }            }

            String id = scanner.nextLine().trim();
            System.out.println("Enter password: ");
            String password = scanner.nextLine().trim();

            try {
                User user = authenticator.login(id, password);
                if (user instanceof Admin) {
                    DisplayUtils.printSuccess("Login Successful!");
                    terminalInterface.showAdminMenu((Admin) user);
                } else if (user instanceof VIPCustomer) {
                    DisplayUtils.printSuccess("Login Successful!");
                    terminalInterface.showVIPCustomerMenu((VIPCustomer) user);
                } else if (user instanceof RegularCustomer) {
                    DisplayUtils.printSuccess("Login Successful!");
                    terminalInterface.showRegularCustomerMenu((RegularCustomer) user);
                }
                DisplayUtils.clearConsole();
                DisplayUtils.printBanner();
                break;
            } catch (InvalidLoginException e) {
                System.out.println(e.getMessage());
                System.out.println("Would you like to try again? (yes/no)");
                String response = scanner.next().trim().toLowerCase();
                scanner.nextLine();
                if (!response.equals("yes") && !response.equals("y")) {
                    DisplayUtils.printInfo("Returning to main menu...");
                    DisplayUtils.pause();
                    DisplayUtils.clearConsole();
                    DisplayUtils.printBanner();
                    return;
                }
            }
        }
    }
    private static void regularLogin(){
        System.out.println("Hello Users");
        System.out.println("To confirm it's you, please: ");
        System.out.println("Enter your Roll No");
    }

    private static void vipLogin(){
        System.out.println("Hello VIP Users");
        System.out.println("To confirm it's you, please: ");
        System.out.println("Enter your Roll No");
    }

    private static void adminLogin(){
        System.out.println("Hello Admins");
        System.out.println("To confirm its you, please: ");
        System.out.println("Enter your unique Admin ID");
    }


    private static void handleSignup(){
        DisplayUtils.clearConsole();
        DisplayUtils.printBanner();
        DisplayUtils.printHeading("Signup Menu");
        System.out.println("Sign up as: ");
        System.out.println("1. Admin SignUp");
        System.out.println("2. VIP SignUp");
        System.out.println("3. Regular SignUp");
        System.out.println("4. <-- Go Back");

        int choice = InputUtils.readInt("Enter your choice: ", 1, 4);
        if (choice == 4) {
            System.out.println("Returning to main menu...");
            DisplayUtils.pause();
            DisplayUtils.clearConsole();
            DisplayUtils.printBanner();
            return; // exit
        }
        System.out.println("Enter name: ");
        String name = scanner.next();
        System.out.println("Enter unique ID (Roll no for students, Admin ID for Admins): ");
        String id = scanner.next();
        System.out.println("Enter password: " );
        String password = scanner.next();

        User newUser;
        try {
            newUser = switch(choice){
                case 1 -> new Admin(name, password, id);
                case 2 -> new VIPCustomer(name, password, id);
                case 3 -> new RegularCustomer(name, password, id);
                default -> throw new IllegalArgumentException("Invalid choice");
            };
        } catch (IllegalArgumentException e) {
            DisplayUtils.printFailure(e.getMessage());
            DisplayUtils.pause();
            return;
        }

        authenticator.signup(newUser);
        DisplayUtils.printSuccess("SignUp Successful!");
        DisplayUtils.pause();
        DisplayUtils.clearConsole();
        DisplayUtils.printBanner();
    }

}
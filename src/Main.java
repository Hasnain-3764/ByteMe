
import java.util.Scanner;

public class Main {
    private static AuthenticationManager authenticator = new AuthenticationManager();
    private static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        while (true) {
            System.out.println("Welcome to IIIT-D Canteen");
            System.out.println("1. Login");
            System.out.println("2. SignUp");
            System.out.println("3. Exit the Application");
            int choice = InputUtils.readInt("Enter your choice: ", 1, 3);
            switch (choice) {
                case 1 -> handleLogin();
                case 2 -> handleSignup();
                case 3 -> System.exit(0);
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }
    private static void handleLogin(){
        System.out.println("Enter ID (Roll no for students, AdminID for admins): ");
        String id = scanner.next();
        System.out.println("Enter password: ");
        String password = scanner.next();

        try{
            User user = authenticator.login(id,password);
            user.viewMenu();
        } catch(InvalidLoginException e){
            System.out.println(e.getMessage());
        }
    }
    private static void handleSignup(){
        System.out.println("Sign up as: ");
        System.out.println("1. Admin");
        System.out.println("2. VIP Customer");
        System.out.println("3. Regular Customer");

        int choice = scanner.nextInt();
        System.out.println("Enter name: ");
        String name = scanner.next();
        System.out.println("Enter unique ID (Roll no for students, Admin ID for Admins): ");
        String id = scanner.next();
        System.out.println("Enter password: " );
        String password = scanner.next();

        User newUser = switch(choice){
            case 1 -> new Admin(name, password, id);
            case 2 -> new VIPCustomer(name, password, id);
            case 3 -> new RegularCustomer(name, password, id);
            default -> throw new IllegalArgumentException("Invalid argument");
        };

        authenticator.signup(newUser);

    }
}
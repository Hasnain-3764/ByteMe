import java.util.Scanner;

public class TerminalInterface {
    private Scanner scanner;

    public TerminalInterface(){
        this.scanner = new Scanner(System.in);
    }
    public void showMainMenu(){
        System.out.println("this is main menu");
        System.out.println("1. Admin Login");
        System.out.println("2. VIP Login");
        System.out.println("3. Regular Login");
        System.out.println("4. Exit");

        int choice = scanner.nextInt();
        switch(choice){
            case 1 -> showAdminMenu();
            case 2 -> showVIPCustomerMenu();
            case 3 -> showRegularCustomerMenu();
            case 4 -> System.out.println("Exiting... Thank You");
        }
    }
    public void showAdminMenu(){
        System.out.println("this is admin menu");
    }
    public void showVIPCustomerMenu(){ System.out.println("this is VIP menu");}
    public void showRegularCustomerMenu(){
        System.out.println("this is customer menu");
    }
    public void showOrderTracking(){
        System.out.println("this is order tracking menu");
    }
    public void handleNavigation(){
        System.out.println("this is navigation handle");
    }
}

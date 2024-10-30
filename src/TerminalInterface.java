import java.util.Scanner;

public class TerminalInterface {
    private Scanner scanner;

    public TerminalInterface(){
        this.scanner = new Scanner(System.in);
    }
    public void showMainMenu(){
        System.out.println("this is main menu");
        System.out.println("1. Admin Login");
        System.out.println("2. Customer Login");
        System.out.println("3. Exit");

        int choice = scanner.nextInt();
        switch(choice){
            case 1 -> showAdminMenu();
            case 2 -> showCustormerMenu();
            case 3 -> System.out.println("Exiting... Thank You");
        }
    }
    public void showAdminMenu(){
        System.out.println("this is admin menu");
    }
    public void showCustormerMenu(){
        System.out.println("this is customer menu");
    }
    public void showOrderTracking(){
        System.out.println("this is order tracking menu");
    }
    public void handleNavigation(){
        System.out.println("this is navigation handle");
    }
}

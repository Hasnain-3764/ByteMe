
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        while(true){
            System.out.println("Welcome to IIIT-D Canteen");
            System.out.println("1. Login");
            System.out.println("2. SignUp");
            System.out.println("3. Exit the Application");

            int choice = InputUtils.readInt("Enter your choice: ", 1, 3);

            switch (choice){
                case 1:
//                    login();
                    break;
                case 2:
//                    signup();
                    break;
                case 3:
                    System.out.println("Bye, See you");
                    return;
                default:
                    System.out.println("Invalid Input");
            }
        }

    }
}
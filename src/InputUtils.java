import java.util.InputMismatchException;
import java.util.Scanner;

public class InputUtils {

    private static Scanner scanner = new Scanner(System.in);

    public static int readInt(String prompt, int min , int max){
        int input = -1;
        boolean invalidInput = true;
        while(invalidInput){
            System.out.println(prompt);
            try{
                input = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if(input>= min && input<=max){
                    invalidInput = false;
                }
                else{
                    System.out.println("Please enter a number between "+min+" and "+max);
                }
            } catch (InputMismatchException e){
                System.out.println("Invalid Input. Please enter a numeric value");
                scanner.nextLine();
            }
        }
        return input;
    }

}

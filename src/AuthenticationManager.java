import java.util.Scanner;

public class AuthenticationManager {
    private static Scanner scanner = new Scanner(System.in);
    public AuthenticationManager(){

    }
    public void adminLogin(){
        System.out.println("Enter your Unique Admin Id: ");
        String adminId = scanner.nextLine();
        System.out.println("Enter your password: ");
        String password = scanner.nextLine();
//        Admin admin = findAdmin(adminId);
//        try{
//            if(admin != null && admin.login(adminId,password)){
//                System.out.println("Admin Login Successful");
//            }
//            else{
//                throw new InvalidLoginException("AdminId not found");
//            }
//        }
//        catch(InvalidLoginException e){
//            System.out.println(" e");
//        }

    }
    public void studentLogin(){

    }
    public void studentSignUp(){

    }
    public void adminSignUp(){

    }
}

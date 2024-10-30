import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;

public class AuthenticationManager {
    private Map<String, User> userMap;
    private static Scanner scanner = new Scanner(System.in);

    public AuthenticationManager(){
        userMap = new HashMap<>();
//        for(User user: )
        List<User> initialUsers = DataInitializer.initilizeUsers();
        for(User user: initialUsers){
            userMap.put(user.getLoginID(), user);
        }
    }
    public User login(String id, String password) throws InvalidLoginException {
        User user = userMap.get(id);
        if (user != null && user.login(id, password)) {
            return user;  // Login successful
        }
        throw new InvalidLoginException("Invalid email or password.");
    }

    // Signup method
    public void signup(User user) {
        if (userMap.containsKey(user.getLoginID())) {
            System.out.println("User already exists.");
        } else {
            userMap.put(user.getLoginID(), user);
            System.out.println("Signup successful!");
        }
    }
}

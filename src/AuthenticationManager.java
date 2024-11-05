import java.util.*;

public class AuthenticationManager {
    private Map<String, User> userMap;
    private static Scanner scanner = new Scanner(System.in);
    private static AuthenticationManager instance;

    // private constructor to avoid instantiation.
    private AuthenticationManager(){
        userMap = new HashMap<>();
        List<User> initialUsers = DataInitializer.initializeUsers();
        for(User user: initialUsers){
            userMap.put(user.getLoginID(), user);
        }
    }

    // to access the only instance(common across all classes)
    public static AuthenticationManager getInstance() {
        if (instance == null) {
            instance = new AuthenticationManager();
        }
        return instance;
    }

    public User login(String id, String password) throws InvalidLoginException {
        User user = userMap.get(id);
        if (user != null && user.login(password)) {
            return user;  // Login successful
        }
        DisplayUtils.printFailure("Invalid ID or password.");
        throw new InvalidLoginException("Invalid ID or password.");
    }

    // Signup method
    public void signup(User user) {
        if (user instanceof Admin) {
            DisplayUtils.printFailure("Admin signup is restricted.");
            return;
        }
        if (userMap.containsKey(user.getLoginID())) {
            DisplayUtils.printFailure("User already exists.");
        } else {
            userMap.put(user.getLoginID(), user);
            DisplayUtils.printSuccess("Signup successful!");
        }
    }



    public void upgradeToVIP(VIPCustomer vipCustomer){
        String loginID = vipCustomer.getLoginID();
        if(userMap.containsKey(loginID)){
            userMap.put(loginID,vipCustomer);
            DisplayUtils.printSuccess("User upgraded to VIP successfully.");
        }
        else{
            DisplayUtils.printFailure("User not found for upgrade.");
        }
    }

    public List<User> getAllUsers(){
        return new ArrayList<>(userMap.values());
    }
}

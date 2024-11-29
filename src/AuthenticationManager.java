import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import org.json.*;

public class AuthenticationManager {
    private static final String USER_DATA_FILE = "data/users.json";
    private Map<String, User> userMap;
    private static Scanner scanner = new Scanner(System.in);
    private static AuthenticationManager instance;

    // private constructor to avoid instantiation.
    private AuthenticationManager(){
        userMap = new HashMap<>();
        loadUsersFromFile(); // to be implemented
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
            saveUsersToFile(); // add to json
            DisplayUtils.printSuccess("Signup successful!");
        }
    }

    public static void resetInstance() {
        instance = null;
    }

    public void upgradeToVIP(VIPCustomer vipCustomer){
        String loginID = vipCustomer.getLoginID();
        if(userMap.containsKey(loginID)){
            userMap.put(loginID,vipCustomer);
            saveUsersToFile(); // update json
            DisplayUtils.printSuccess("User upgraded to VIP successfully.");
        }
        else{
            DisplayUtils.printFailure("User not found for upgrade.");
        }
    }

    public List<User> getAllUsers(){
        return new ArrayList<>(userMap.values());
    }

    public void loadUsersFromFile() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(USER_DATA_FILE)));
            JSONArray usersArray = new JSONArray(content); // array of all users.
            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject userJSON = usersArray.getJSONObject(i);
                User user = User.fromJSON(userJSON);// taking users from userJSON
                userMap.put(user.getLoginID(), user);// and putting in userMap
            }
        } catch (IOException e) {
            System.out.println("User data file not found. Starting with initial users.");
            List<User> initialUsers = DataInitializer.initializeUsers();
            for(User user: initialUsers){
                userMap.put(user.getLoginID(), user);
            }
        }
    }

    public void saveUsersToFile() {
        JSONArray usersArray = new JSONArray();
        for (User user : userMap.values()) {
            usersArray.put(user.toJSON());
        }
        try (FileWriter file = new FileWriter(USER_DATA_FILE)) {
            file.write(usersArray.toString(4));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

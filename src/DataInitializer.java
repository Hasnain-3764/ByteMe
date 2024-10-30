import java.util.ArrayList;
import java.util.List;

public class DataInitializer {
    public static List<User> initilizeUsers(){
        List<User> users = new ArrayList<>();
        users.add(new Admin("Admin1","pswd1","A1"));
        users.add(new VIPCustomer("vipUser1", "vipPswd1","2023325"));
        users.add(new RegularCustomer("cust1","custPswd1","2023467"));

        return users;
    }
}

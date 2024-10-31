public class OrderManagerImpl implements OrderManager {
    private static OrderManagerImpl instance;

    private OrderManagerImpl() {

    }

    public static OrderManagerImpl getInstance(){
        if(instance == null){
            instance = new OrderManagerImpl();
        }
        return instance;
    }
}

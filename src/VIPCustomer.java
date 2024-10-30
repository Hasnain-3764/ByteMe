public class VIPCustomer extends Customer{

    public VIPCustomer(String name, String password, String rollNo){
        super(name, password, rollNo);
    }

    @Override
    public void placeOrder(Order order) {
        System.out.println("fast fast ordering for vips.");
    }
    public void accessVIPBenefits(){

    }

}

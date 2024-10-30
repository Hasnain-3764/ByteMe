public class Admin extends User{
    private String adminId;

    public Admin(String name, String password, String adminId){
        super(name,password);
        this.adminId = adminId;
    }

    @Override
    public String getLoginID() {
        return adminId;
    }


    public void manageMenu(){
        System.out.println("Admin Managing Menu");
    }
    public void processOrder(){
        System.out.println("Admin Processing Order");
    }
    public void generateReport(){
        System.out.println("Admin Generating Reports");
    }
    public void processRefund(Order order){
        System.out.println("Admin Processing Refunds");
    }



}

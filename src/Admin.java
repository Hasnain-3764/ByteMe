public class Admin extends User{
    private String adminId;
    public Admin(String name, String password, String adminId){
        super(name,password);
        this.adminId = adminId;
    }

    @Override
    public boolean login(String adminId, String password) throws InvalidLoginException {
        if(!this.adminId.equals(adminId) || !this.password.equals(password)) {
            throw new InvalidLoginException("Invalid userid or password");
        }
        return true;
    }
    public void manageMenu(){

    }
    public void processOrder(){

    }
    public void generateReport(){

    }
    public void processRefunt(Order order){

    }



}

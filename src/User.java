public abstract class User {
    protected String name;
    protected String rollNo;
    protected String password;
    public User(String name, String password){
        this.name = name;
        this.password = password;
    }
    public abstract String getLoginID();  // To get unique ID for each user type
    public abstract void viewMenu();  // Abstract method for role-specific menus

//    public abstract boolean login(String rollNo, String password) throws InvalidLoginException;

    public boolean login(String id, String password) {
        return this.getLoginID().equals(id) && this.password.equals(password);
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }


}

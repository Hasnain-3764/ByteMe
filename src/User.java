public abstract class User {
    protected String name;
    protected String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public abstract String getLoginID();  // Each subclass will define its login ID

    public boolean login(String password) {
        return this.password.equals(password);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

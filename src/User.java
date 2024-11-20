import org.json.JSONObject;

import java.io.Serializable;

public abstract class User implements Serializable {
    protected String name;
    protected String password;
    private static final long serialVersionUID = 1L;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public abstract JSONObject toJSON();

    public static User fromJSON(JSONObject json){
        String type = json.getString("type");
        switch(type){
            case "Admin":
                return Admin.fromJSON(json);
            case "VIPCustomer":
                return VIPCustomer.fromJSON(json);
            case "RegularCustomer":
                return RegularCustomer.fromJSON(json);
            default:
                throw new IllegalArgumentException("Unknown user type: " + type);
        }
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

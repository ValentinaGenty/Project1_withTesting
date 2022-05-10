package Entity;

public class User {
    private int id;
    private String name;

    private String type;
    private String username;
    private String password;


    //empty constructor
    public User(){};

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUser_id() {
        return id;
    }

    public void setUser_id(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String name, String type, String username, String password) {
        this.name = name;
        this.type = type;
        this.username = username;
        this.password = password;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User( int id, String name, String type, String username, String password) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.username = username;
        this.password = password;
    }
}

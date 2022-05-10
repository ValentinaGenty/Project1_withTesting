package Entity;

public class User {

    private String _firstName;
    private String _lastName;
    private String _email;
    private int _userid;
    private String type;
    private String username;
    private String password;


    //empty constructor
    public User(){};

    public User(String _firstName, String _lastName, String _email){
        this._firstName = _firstName;
        this._lastName = _lastName;
        this._email = _email;
    }

    public User(int _userid){
        this._userid = _userid;
    }

    public User(int _userid, String _firstName, String _lastName, String _email){
        this._userid = _userid;
        this._firstName = _firstName;
        this._lastName = _lastName;
        this._email = _email;
    }


    public int get_userid() {
        return _userid;
    }

    public void set_userid(int _userid) {
        this._userid = _userid;
    }

    public String get_firstName() {
        return _firstName;
    }

    public void set_firstName(String _firstName) {
        this._firstName = _firstName;
    }

    public String get_lastName() {
        return _lastName;
    }

    public void set_lastName(String _lastName) {
        this._lastName = _lastName;
    }

    public String get_email() {
        return _email;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    @Override
    public String toString() {
        return "User{" +
                "_firstName='" + _firstName + '\'' +
                ", _lastName='" + _lastName + '\'' +
                ", _email='" + _email + '\'' +
                ", _userId=" + _userid +
                '}';
    }
}

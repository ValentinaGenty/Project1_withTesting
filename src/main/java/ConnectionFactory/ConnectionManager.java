package ConnectionFactory;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectionManager {

    private static Connection connection = null;

    private ConnectionManager(){

    }

    //this method will return a connection to the sql
    public static Connection getConnection() {
        if (connection == null){
            try{
                Class.forName("org.postgresql.Driver");
            } catch(ClassNotFoundException e){
                e.printStackTrace();;
            }
            //ResourceBundle bundle = ResourceBundle.getBundle("jdbc");
            //String url = bundle.getString("url");
            //String username = bundle.getString("username");
            //String password = bundle.getString("password");
            String url = "jdbc:postgresql://localhost:5432/project1";
            String username = "postgres";
            String password = "1234";

            try {
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException e){
                System.out.println("There was a problem " );
                e.printStackTrace();
            }
        }
        return connection;
    }
}
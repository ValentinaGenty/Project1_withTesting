package ConnectionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectionFactory {

    // this is our connection to the SQL database:
    private static Connection connection = null;

    // make a private constructor, we can't manually instantiate this factory
    private ConnectionFactory() {

    }

    // this method will return a connection the SQL
    public static Connection getConnection() {
        if(connection == null) {
            // with this boolean, we can determine whether we're testing or not:
            boolean test = true;
            // use ternary operator to determine the driver and config file:
            String driver = test ? "org.h2.Driver" : "org.postgresql.Driver";
            String configFile = test ? "dbConfigTest" : "jdbc";
            // Specify the database driver:
            try {
                Class.forName(driver);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            ResourceBundle bundle = ResourceBundle.getBundle(configFile);
            String url = bundle.getString("url");
            String username = bundle.getString("username");
            String password = bundle.getString("password");

            try {
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                System.out.println("Something went wrong when creating the connection!");
                e.printStackTrace();
            }
        }
        return connection;
    }
}
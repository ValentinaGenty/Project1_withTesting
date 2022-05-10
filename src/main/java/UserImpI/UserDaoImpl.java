


package UserImpI;

import ConnectionFactory.ConnectionManager;
import Entity.User;
import UserDao.UserDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    Connection connection;


    public UserDaoImpl(){
        connection = ConnectionManager.getConnection();
    }

    @Override
    public void create(User user) {
        String query = "insert into users (_firstname, _lastname, _email) values (?,?,?);";
        try {
            PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.get_firstName());
            statement.setString(2, user.get_lastName());
            statement.setString(3, user.get_email());
            int count = statement.executeUpdate();
            if(count == 1){
                ResultSet resultSet = statement.getGeneratedKeys();
                resultSet.next();
                int _userId = resultSet.getInt("_userid");
                System.out.println("generated user _userId is: " + _userId);
            }

        }catch (SQLException ex){
            System.out.println(ex.getLocalizedMessage());
        }
    }

    @Override
    public void update(User user) {
        String query = "Update users set _firstname = ?, _lastname = ?, _email = ? where _userid = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.get_firstName());
            statement.setString(2, user.get_lastName());
            statement.setString(3, user.get_email());
            statement.setInt(4, user.get_userid());
            int count = statement.executeUpdate();
            if(count == 1){
                System.out.println("There has been a update that was successful");
            }


        }catch (SQLException ex){
            System.out.println(ex.getLocalizedMessage());
        }
    }

    @Override
    public void delete(User user) {
        String query = "Delete from users where _userid = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, user.get_userid());
            int count = statement.executeUpdate();
            if(count == 1){
                System.out.println("delete successful");
            }


        }catch (SQLException ex){
            System.out.println(ex.getLocalizedMessage());
        }
    }

    @Override
    public User get(int _userId) {
        String query = "select * from users where _userid = ?;";
        User user = null;
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, _userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                user = getUserFromResultSet(resultSet);
            }
        }catch(Exception ex) {
            System.out.println("Something went wrong");
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        String query = "select * from users;";
        List<User> users = new ArrayList<>();
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
              User user = getUserFromResultSet(resultSet);
              users.add(user);
            }
        }catch(Exception ex) {
            System.out.println("Something went wrong");
        }
        return users;
    }

    private User getUserFromResultSet(ResultSet resultSet){
        try {
            int _userId = resultSet.getInt("_userid");
            String _firstName = resultSet.getString("_firstname");
            String _lastName = resultSet.getString("_lastname");
            String _email = resultSet.getString("_email");
            return new User(_userId, _firstName, _lastName, _email);

        }catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        return null;
    }
}

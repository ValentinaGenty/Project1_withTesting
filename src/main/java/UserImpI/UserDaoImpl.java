package UserImpI;

import ConnectionFactory.ConnectionManager;
import CustomArrayList.CustomList;
import CustomArrayList.CustomArrayList;
import Entity.User;
import UserDao.UserDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    Connection connection;


    public UserDaoImpl(){
        connection = ConnectionManager.getConnection();
    }

    @Override
    public void create(User user) {
        String query = "insert into users (name, type, username, password) values (?,?,?,?);";
        try {
            PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, user.getType());
            statement.setString(3, user.getUsername());
            statement.setString(4, user.getPassword());

            int count = statement.executeUpdate();
            if(count == 1){
                ResultSet resultSet = statement.getGeneratedKeys();
                resultSet.next();
                int _userId = resultSet.getInt("id");
                System.out.println("generated user user ID is: " + _userId);
            }

        }catch (SQLException ex){
            System.out.println(ex.getLocalizedMessage());
        }
    }

    @Override
    public void update(User user) {
        String query = "Update users set name = ?, type = ?, username = ? , password = ? where id = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getName());
            statement.setString(2, user.getType());
            statement.setString(3, user.getUsername());
            statement.setString(4, user.getPassword());
            statement.setInt(5, user.getUser_id());

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
        String query = "Delete from users where id = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, user.getUser_id());
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
        String query = "select * from users where id = ?;";
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
    public User get(User entered_user) {
        String query = "select * from users where username = ?;";
        User user = null;
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, entered_user.getUsername());
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
    public CustomList<User> getAll() {
        String query = "select * from users;";
        CustomList<User> users = new CustomArrayList<User>();
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
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String type = resultSet.getString("type");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            return new User(id, name, type, username, password);

        }catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public void initTables(){
        String sql="DROP TABLE users IF EXISTS; CREATE TABLE users(id SERIAL PRIMARY KEY, name VARCHAR(50), type VARCHAR(50), username VARCHAR(50), password VARCHAR(50)); ";
        try{
            Statement statement = connection.createStatement();
            statement.execute(sql);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    @Override
    public void fillTables(){
        String sql = "insert into users(id, name, type, username, password) values(default,'george','employee','anakin','vader');\n";
        try {
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch(SQLException e) {
            e.printStackTrace();
        }

    }
}

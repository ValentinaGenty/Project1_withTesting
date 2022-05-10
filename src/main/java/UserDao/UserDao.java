package UserDao;

import CustomArrayList.CustomList;
import Entity.User;

import java.util.List;

public interface UserDao {

    void create(User user);
    void update(User user);
    void delete(User user);
    User get(int _userId);
    User get(User user);
    CustomList<User> getAll();
    void initTables();
    void fillTables();
}

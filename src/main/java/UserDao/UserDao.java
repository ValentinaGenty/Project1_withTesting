package UserDao;



import Entity.User;

import java.util.List;

public interface UserDao {

    void create(User user);
    void update(User user);
    void delete(User user);
    User get(int _userId);
    List<User> getAll();
}

package UserDao;

import TicketDao.TicketDao;
import TicketImpl.TicketDaoImpl;
import UserImpI.UserDaoImpl;

public class UserDaoFactory {
    private static UserDao userDao;

    // private constructor, intentionally disallow instantiation of this class:
    private UserDaoFactory() {

    }

    // check if null, return the dao
    public static UserDao getUserDao() {
        if (userDao == null) {
            // we only ever call this once, we're reusing the same instance:
            userDao = new UserDaoImpl();
        }
        return userDao;
    }


}

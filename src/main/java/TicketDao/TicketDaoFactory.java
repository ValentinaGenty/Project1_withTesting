package TicketDao;

import TicketImpl.TicketDaoImpl;

public class TicketDaoFactory {
    private static TicketDao ticketDao;

    // private constructor, intentionally disallow instantiation of this class:
    private TicketDaoFactory() {

    }

    // check if null, return the dao
    public static TicketDao getTicketDao() {
        if (ticketDao == null) {
            // we only ever call this once, we're reusing the same instance:
            ticketDao = new TicketDaoImpl();
        }
        return ticketDao;
    }


}

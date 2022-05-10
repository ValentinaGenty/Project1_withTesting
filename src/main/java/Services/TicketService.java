package Services;

import CustomArrayList.CustomList;
import CustomArrayList.CustomArrayList;
import Entity.Ticket;
import TicketDao.TicketDao;
import TicketDao.TicketDaoFactory;

import java.util.List;

public class TicketService {

    public static CustomList<Ticket> getAllTicketsById(int id) {
        TicketDao ticketDao = TicketDaoFactory.getTicketDao();
        return ticketDao.getAllTicketsById(id);
    }

    public static CustomList<Ticket> getAllTickets() {
        TicketDao ticketDao = TicketDaoFactory.getTicketDao();
        return ticketDao.getAllTickets();
    }

    public static CustomList<Ticket> getAllPendingTickets() {
        TicketDao ticketDao = TicketDaoFactory.getTicketDao();
        return ticketDao.getAllPendingTickets();
    }



}

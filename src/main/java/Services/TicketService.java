package Services;

import Entity.Ticket;
import TicketDao.TicketDao;
import TicketDao.TicketDaoFactory;

import java.util.List;

public class TicketService {

    public static List<Ticket> getAllTicketsById(int id) {
        TicketDao ticketDao = TicketDaoFactory.getTicketDao();
        return ticketDao.getAllTicketsById(id);
    }

    public static List<Ticket> getAllTickets() {
        TicketDao ticketDao = TicketDaoFactory.getTicketDao();
        return ticketDao.getAllTickets();
    }

    public static void create(Ticket ticket) {
        TicketDao ticketDao = TicketDaoFactory.getTicketDao();
        ticketDao.create(ticket);
    }



}

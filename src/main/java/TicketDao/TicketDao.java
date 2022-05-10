package TicketDao;

import CustomArrayList.CustomArrayList;
import Entity.Ticket;
import Entity.TimeStampWrapper;
import Entity.User;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public interface TicketDao {
    CustomArrayList<Ticket> getAll();
    //List<Ticket> getAll();
    List<Ticket> getAllPastTickets(User user);
    List<Ticket> getAllPendingTickets(Ticket ticket);
    List<Ticket> getAllPastTicketsByDate();
    List<Ticket> getAllTicketsById(int id);
    List<Ticket> getAllTickets();
    void create(Ticket ticket);
}

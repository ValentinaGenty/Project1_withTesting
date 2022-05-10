package TicketDao;

import CustomArrayList.CustomList;
import Entity.Ticket;
import Entity.TimeStampWrapper;
import Entity.User;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public interface TicketDao {

    CustomList<Ticket> getAll();
    CustomList<Ticket> getAll(int entered_ticket);
    CustomList<Ticket> getAllPastTickets();
    CustomList<Ticket> getAllPastTickets(int entered_ticket);
    CustomList<Ticket> getAllPendingTickets();
    CustomList<Ticket> getAllPendingTickets(int entered_ticket);
    CustomList<Ticket> getAllPastTicketsByDate();
    CustomList<Ticket> getAllPastTicketsByDate(int user_id);
    void processTicket(Ticket ticket);
    Ticket getTicketByTicketId(int ticket_id);
    CustomList<Ticket> getAllTicketsById(int id);
    CustomList<Ticket> getAllTickets();
    void create(Ticket ticket);
}

package TicketImpl;
import CustomArrayList.CustomArrayList;
import Entity.TimeStampWrapper;
import TicketDao.TicketDao;
import ConnectionFactory.ConnectionManager;
import Entity.Ticket;
import Entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TicketDaoImpl implements TicketDao {

    Connection connection;


    public TicketDaoImpl(){
        connection = ConnectionManager.getConnection();
    }

    @Override
    public List<Ticket> getAllTicketsById(int id) {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "select * from ticket_system where user_id = ? and status = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // set the id using the id that we passed in:
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, "pending");
            ResultSet resultSet = preparedStatement.executeQuery();
            // checking, do we have a book from this query
            if (resultSet.next()) {
                // extract out the data
                Ticket ticket = getTicket(resultSet);
                tickets.add(ticket);
              }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    @Override
    public List<Ticket> getAllTickets() {
        return null;
    }

    public Ticket getTicket(ResultSet resultSet){
        try{
            int ticketid = resultSet.getInt("ticket_id");
            String status = resultSet.getString("status");
            String reason = resultSet.getString("amount");
            float amount = resultSet.getFloat("amount");
            return new Ticket(ticketid, reason, amount, status);
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }



    //NEVER TOUCH FINISHED
    @Override
    public void create(Ticket ticket) {
        String query = "insert into ticket_system (reason, amount, submitted_date, status, user_id) values (?,?,?,?,?);";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());;
        try {
            PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, ticket.getReason());
            statement.setDouble(2, ticket.getAmount());
            statement.setTimestamp(3, timestamp);
            statement.setString(4, ticket.getStatus());
            statement.setInt(5, ticket.getUser_id());
            int count = statement.executeUpdate();
            if(count == 1){
                ResultSet resultSet = statement.getGeneratedKeys();
                resultSet.next();
                int ticket_id = resultSet.getInt("ticket_id");
                System.out.println("generated user ticket_id is: " + ticket_id);
            }

        }catch (SQLException ex){
            System.out.println(ex.getLocalizedMessage());
        }
    }

    //Finshed
    public List<Ticket> getAllPendingTickets(Ticket ticket) {
        String query = "select * from ticket_system where user_id = ? and status = ?;";
        List<Ticket> tickets = new ArrayList<>();
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, ticket.getUser_id());
            statement.setString(2,ticket.getStatus());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                ticket = getAllTicketsByDateFromResultSet(resultSet);
                tickets.add(ticket);
                System.out.println("The ticket is: " +  ticket.toString());
            }
        }catch(Exception ex) {
            System.out.println("Something went wrong");
            System.out.println(ex);
        }
        return tickets;
    }


    @Override
    public CustomArrayList<Ticket> getAll() {
        String query = "select * from tickets;";
        CustomArrayList<Ticket> tickets = new CustomArrayList<Ticket>();
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Ticket ticket = getTicketFromResultSet(resultSet);
                tickets.add(ticket);
            }
        }catch(Exception ex) {
            System.out.println("Something went wrong");
        }
        return tickets;
    }
//NEVER TOUCH FINISHED
    @Override
    public List<Ticket> getAllPastTickets(User user) {
        String query = "select * from ticket_system where user_id = ? and status = 'rejected' or status = 'approved';";
        List<Ticket> tickets = new ArrayList<>();
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, user.get_userid());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Ticket ticket = getApprovedOrRejectedFromResultSet(resultSet);
                tickets.add(ticket);
            }
        }catch(Exception ex) {
            System.out.println("Something went wrong");
            System.out.println(ex);
        }
        return tickets;
    }


    public List<Ticket> getAllPastTicketsByDate(Ticket ticket) {
        String query = "select * from ticket_system where date = ?;";
        List<Ticket> tickets = new ArrayList<>();
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setTimestamp(1, ticket.getDate());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                ticket = getAllTicketsByDateFromResultSet(resultSet);
                tickets.add(ticket);
                System.out.println("The ticket is: " +  ticket.toString());
            }
        }catch(Exception ex) {
            System.out.println("Something went wrong");
            System.out.println(ex);
        }
        return tickets;
    }

    public List<Ticket> getAllPastTicketsByDate() {
        //String sqlTimeStampConversion = timestamp.getTimestamp().toString().substring(0,19);
        //Timestamp times = Timestamp.valueOf(sqlTimeStampConversion);
        String query = "select * from ticket_system order by submitted_date desc;";
        List<Ticket> tickets = new ArrayList<>();
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            //statement.setInt(1, user.get_userid());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Ticket ticket = getAllTicketsByDateFromTimeStampResultSet(resultSet);
                tickets.add(ticket);
                System.out.println("The ticket is: " +  ticket.toString());
            }
        }catch(Exception ex) {
            System.out.println("Something went wrong");
            System.out.println(ex);
        }
        return tickets;
    }


//get all tickets
    private Ticket getTicketFromResultSet(ResultSet resultSet){
        try {
            int ticket_id = resultSet.getInt("ticket_id");
            String reason = resultSet.getString("reason");
            return new Ticket(ticket_id, reason);

        }catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        return null;
    }

    //get approved or rejected tickets
    private Ticket getApprovedOrRejectedFromResultSet(ResultSet resultSet){
        try {
            int ticket_id = resultSet.getInt("ticket_id");
            String reason = resultSet.getString("reason");
            float amount = resultSet.getFloat("amount");
            Timestamp time = resultSet.getTimestamp("submitted_date");
            String status = resultSet.getString("status");
            int user_id = resultSet.getInt("user_id");
            return new Ticket(ticket_id, reason, amount, time, status, user_id);

        }catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        return null;
    }

    //get all tickets by date
    private Ticket getAllTicketsByDateFromResultSet(ResultSet resultSet){
        try {
            int ticket_id = resultSet.getInt("ticket_id");
            String reason = resultSet.getString("reason");
            Timestamp date = resultSet.getTimestamp("submitted_date");
            return new Ticket(ticket_id, reason, date);

        }catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        return null;
    }

    private Ticket getAllTicketsByDateFromTimeStampResultSet(ResultSet resultSet){
        try {
            int ticket_id = resultSet.getInt("ticket_id");
            String reason = resultSet.getString("reason");
            Timestamp date = resultSet.getTimestamp("submitted_date");
            return new Ticket(ticket_id, reason, date);

        }catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        return null;
    }
}

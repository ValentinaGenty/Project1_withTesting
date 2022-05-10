package TicketImpl;
import CustomArrayList.CustomArrayList;
import CustomArrayList.CustomList;
import Entity.TimeStampWrapper;
import TicketDao.TicketDao;
import ConnectionFactory.ConnectionManager;
import Entity.Ticket;
import Entity.User;

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
    public CustomList<Ticket> getAllTicketsById(int id) {
        CustomList<Ticket> tickets = new CustomArrayList<Ticket>();
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
    public CustomList<Ticket> getAllTickets() {
        return null;
    }

    public Ticket getTicket(ResultSet resultSet){
        try{
            int ticket_id = resultSet.getInt("ticket_id");
            String status = resultSet.getString("status");
            String reason = resultSet.getString("reason");
            float amount = resultSet.getFloat("amount");
            return new Ticket(ticket_id, reason, amount, status);
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }



    //NEVER TOUCH FINISHED
    @Override
    public void create(Ticket ticket) {
        String query = "insert into ticket_system (reason, amount, submitted_date, status, user_id) values (?,?,?,?,?);";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        TimeStampWrapper wrapped = new TimeStampWrapper();
        wrapped.setTimestamp(timestamp);
        try {
            PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, ticket.getReason());
            statement.setDouble(2, ticket.getAmount());
            statement.setTimestamp(3, wrapped.getTimestamp());
            statement.setString(4, "pending");
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



    @Override
    public void processTicket(Ticket ticket){
        String sql = "update ticket_system set status = ? where ticket_id =?;";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,ticket.getStatus());
            statement.setInt(2, ticket.getTicket_id());

            int count = statement.executeUpdate();
            if(count == 1){
                ResultSet resultSet = statement.getGeneratedKeys();
                resultSet.next();
                //int ticket_id = resultSet.getInt("ticket_id");
                //String status = resultSet.getString("status");
                System.out.println("ticket_id: "+ticket.getTicket_id()+"is: " +ticket.getStatus());
            }
        }
        catch(SQLException e){
            e.printStackTrace();;
        }

    }
    @Override
    public Ticket getTicketByTicketId(int ticket_id){
        String query = "select * from ticket_system where ticket_id = ?;";
        Ticket ticket=null;
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, ticket_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                ticket = getTicketFromResultSet(resultSet);
                System.out.println("The ticket is updated: " +  ticket.toString());
            }
        }catch(Exception ex) {
            System.out.println("Something went wrong");
            System.out.println(ex);
        }
        return ticket;
    }

    @Override
    public CustomList<Ticket> getAll() {
        String query = "select * from ticket_system;";
        CustomList<Ticket> tickets = new CustomArrayList<Ticket>();
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

    public CustomList<Ticket> getAll(int entered_ticket) {
        String query = "select * from ticket_system where user_id=?;";
        CustomList<Ticket> tickets = new CustomArrayList<Ticket>();
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,entered_ticket);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Ticket ticket = getTicketFromResultSet(resultSet);
                tickets.add(ticket);
            }
        }catch(Exception ex) {
            System.out.println("Something went wrong");
            System.out.println(ex);
        }
        return tickets;
    }

//NEVER TOUCH FINISHED
    @Override
    public CustomList<Ticket> getAllPastTickets() {
        String query = "select * from ticket_system where status = 'denied' or status = 'approved';";
        CustomList<Ticket> tickets = new CustomArrayList<Ticket>();
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Ticket ticket = getTicketFromResultSet(resultSet);
                tickets.add(ticket);
            }
        }catch(Exception ex) {
            System.out.println("Something went wrong");
            System.out.println(ex);
        }
        return tickets;
    }

    public CustomList<Ticket> getAllPastTickets(int entered_ticket) {
        String query = "select * from ticket_system where user_id=? and (status = 'denied' or status = 'approved');";
        CustomList<Ticket> tickets = new CustomArrayList<Ticket>();
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, entered_ticket);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Ticket ticket = getTicketFromResultSet(resultSet);
                tickets.add(ticket);
            }
        }catch(Exception ex) {
            System.out.println("Something went wrong");
            System.out.println(ex);
        }
        return tickets;
    }

    @Override
    public CustomList<Ticket> getAllPastTicketsByDate() {
        //String sqlTimeStampConversion = timestamp.getTimestamp().toString().substring(0,19);
        //Timestamp times = Timestamp.valueOf(sqlTimeStampConversion);
        String query = "select * from ticket_system order by submitted_date asc;";
        CustomList<Ticket> tickets = new CustomArrayList<Ticket>();
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            //statement.setInt(1, user.get_userid());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Ticket ticket = getTicketFromResultSet(resultSet);
                tickets.add(ticket);
                System.out.println("The ticket is: " +  ticket.toString());
            }
        }catch(Exception ex) {
            System.out.println("Something went wrong");
            System.out.println(ex);
        }
        return tickets;
    }

    public CustomList<Ticket> getAllPastTicketsByDate(int user_id) {
        //String sqlTimeStampConversion = timestamp.getTimestamp().toString().substring(0,19);
        //Timestamp times = Timestamp.valueOf(sqlTimeStampConversion);
        String query = "select * from ticket_system where user_id=? order by submitted_date desc;";
        CustomList<Ticket> tickets = new CustomArrayList<Ticket>();
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, user_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Ticket ticket = getTicketFromResultSet(resultSet);
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
    public CustomList<Ticket> getAllPendingTickets() {
        String query = "select * from ticket_system where status = 'pending';";
        CustomList<Ticket> tickets = new CustomArrayList<Ticket>();
        Ticket ticket=null;
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                ticket = getTicketFromResultSet(resultSet);
                tickets.add(ticket);
            }
        }catch(Exception ex) {
            System.out.println("Something went wrong");
            System.out.println(ex);
        }
        return tickets;
    }

    public CustomList<Ticket> getAllPendingTickets(int entered_ticket) {
        String query = "select * from ticket_system where status = 'pending' and user_id=?;";
        CustomList<Ticket> tickets = new CustomArrayList<Ticket>();
        Ticket ticket=null;
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, entered_ticket);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                ticket = getTicketFromResultSet(resultSet);
                tickets.add(ticket);
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
            float amount = resultSet.getFloat("amount");
            String status = resultSet.getString("status");
            int user_id = resultSet.getInt("user_id");
            Timestamp submitted_date = resultSet.getTimestamp("submitted_date");
            return new Ticket(ticket_id, reason, amount, status, user_id, submitted_date);

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

    @Override
    public void initTables(){
        String sql="DROP TABLE ticket_system IF EXISTS; CREATE TABLE ticket_system(ticket_id SERIAL PRIMARY KEY, user_id INTEGER, reason VARCHAR(200), amount FLOAT(8), status VARCHAR(50), Submitted_date Timestamp, CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES users(id) name VARCHAR(50)); ";
        try{
            Statement statement = connection.createStatement();
            statement.execute(sql);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    @Override
    public void fillTables(){
        String sql = "insert into ticket_system(ticket_id,user_id,reason,amount,status,submitted_date) values (default, 1, 'new desk chair', 200.00,'pending',2022-05-09";
        try {
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}

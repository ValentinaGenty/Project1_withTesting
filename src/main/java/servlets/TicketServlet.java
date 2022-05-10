package servlets;

import CustomArrayList.CustomList;
import CustomArrayList.CustomArrayList;
import Entity.Ticket;
import Entity.User;
import TicketDao.TicketDao;
import TicketImpl.TicketDaoImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TicketServlet extends HttpServlet {

    private TicketDao ticketDao = new TicketDaoImpl();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type="";
        String user_id="";
        CustomList<Ticket> tickets=null;
        ObjectMapper mapper = new ObjectMapper();

        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try {
            type=req.getParameter("type");
            user_id=req.getParameter("user_id");
            if(type==null){
                if(user_id==null){
                    tickets = ticketDao.getAll();
                }
                else{
                    tickets = ticketDao.getAll(Integer.parseInt(user_id));
                }

            }
            else if(type.equals("past")){
                if(user_id==null) {
                    tickets = ticketDao.getAllPastTickets();
                }
                else{
                    tickets = ticketDao.getAllPastTickets(Integer.parseInt(user_id));
                }

            } else if (type.equals("pending")) {
                if(user_id==null) {
                    tickets = ticketDao.getAllPendingTickets();
                }
                else{
                    tickets = ticketDao.getAllPendingTickets(Integer.parseInt(user_id));
                }

            }
            else if(type.equals("sorted")){
                if(user_id==null) {
                    tickets= ticketDao.getAllPastTicketsByDate();
                }
                else{
                    tickets = ticketDao.getAllPastTicketsByDate(Integer.parseInt(user_id));
                }

            }


            String json="";
            for(int i=0;i<tickets.size();i++){
                json+=mapper.writeValueAsString(tickets.get(i))+"\n";
            }
            resp.getWriter().print(json);
            resp.setStatus(200);

        }catch (Exception ex){
            resp.setStatus(500);
            System.out.println(ex);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            Ticket payload = mapper.readValue(req.getInputStream(), Ticket.class);
            ticketDao.create(payload);
            resp.setStatus(203);
            resp.getWriter().print("Ticket successfully added");
        }catch (IOException ex){
            resp.setStatus(500);
            resp.getWriter().print("Something went wrong");
            System.out.println(ex.getLocalizedMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse res) throws  IOException{
        res.setContentType("application/json");
        try{
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            Ticket payload = mapper.readValue(req.getInputStream(), Ticket.class);
            Ticket ticket=ticketDao.getTicketByTicketId(payload.getTicket_id());
            ticket.setStatus(payload.getStatus());
            ticketDao.processTicket(ticket);
        }
        catch(NumberFormatException e){
            e.printStackTrace();
        }




    }
}

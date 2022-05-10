package servlets;

import Entity.Ticket;
import Entity.User;
import TicketDao.TicketDao;
import TicketImpl.TicketDaoImpl;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetPastTicketsServlet extends HttpServlet {

    private TicketDao ticketDao = new TicketDaoImpl();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setStatus(200);
        try {

            ObjectMapper mapper = new ObjectMapper();
            User payload = mapper.readValue(req.getInputStream(), User.class);
            List<Ticket> tickets = ticketDao.getAllPastTickets(payload);
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            String json = mapper.writeValueAsString(tickets);
            resp.getWriter().print(json);
            resp.setStatus(200);
        }catch (Exception ex){
            resp.setStatus(500);
            System.out.println(ex);
        }
    }
}

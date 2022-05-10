/*package servlets;

import CustomArrayList.CustomList;
import Entity.Ticket;
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

public class GetPastTicketsServlet extends HttpServlet {

    private TicketDao ticketDao = new TicketDaoImpl();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(200);
        resp.setContentType("application/json");

        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        int idToGet;
        try {
            idToGet=Integer.parseInt(req.getParameter("id"));

        }catch (Exception ex){
            resp.setStatus(500);
            System.out.println(ex);
        }

        CustomList<Ticket> tickets = ticketDao.getAllPastTickets();
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String json = mapper.writeValueAsString(tickets);
        resp.getWriter().print(json);
        resp.setStatus(200);
    }
}*/

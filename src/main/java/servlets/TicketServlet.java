/*

package servlets;

import CustomArrayList.CustomArrayList;
import Entity.Ticket;
import TicketDao.TicketDao;
import TicketImpl.TicketDaoImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TicketServlet extends HttpServlet {

    private TicketDao ticketDao = new TicketDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(200);
        try {
            CustomArrayList<Ticket> tickets = ticketDao.getAll();
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(tickets);
            resp.getWriter().print(json);
            resp.setStatus(200);
        }catch (Exception ex){
            resp.setStatus(500);
            System.out.println(ex);
        }
    }
}
*/
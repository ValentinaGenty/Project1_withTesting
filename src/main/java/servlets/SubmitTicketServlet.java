/*package servlets;

import Entity.Ticket;
import Entity.User;
import TicketDao.TicketDao;
import TicketImpl.TicketDaoImpl;
import UserDao.UserDao;
import UserImpI.UserDaoImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SubmitTicketServlet extends HttpServlet {

    private TicketDao ticketDao = new TicketDaoImpl();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
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


}*/

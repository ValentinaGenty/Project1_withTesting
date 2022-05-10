package servlets;

import Entity.Ticket;
import Entity.TimeStampWrapper;
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
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

public class ViewTicketsByDateServlet extends HttpServlet {

    private TicketDao ticketDao = new TicketDaoImpl();
/*
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(200);
        try {
            List<Ticket> tickets = ticketDao.getAll();
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


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
           // mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            //User payload = mapper.readValue(req.getInputStream(), User.class);
            List<Ticket> tickets = ticketDao.getAllPastTicketsByDate();
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            String json = mapper.writeValueAsString(tickets);
            resp.getWriter().print(json);
            resp.setStatus(203);
            resp.getWriter().print("Ticket successfully displayed");
        }catch (IOException ex){
            resp.setStatus(500);
            resp.getWriter().print("Something went wrong");
            System.out.println(ex.getLocalizedMessage());
        }
    }

}


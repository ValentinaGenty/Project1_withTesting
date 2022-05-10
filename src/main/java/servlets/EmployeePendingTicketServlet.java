package servlets;

import CustomArrayList.CustomList;
import Entity.Ticket;
import Services.TicketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class EmployeePendingTicketServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("application/json");
        // set up writer:
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);


        // this will store the id that we pass in through postman:
        int idToGet;
        try {
            // try to parse the id from parameters. If it fails, that means we didn't pass one in:
            idToGet = Integer.parseInt(req.getParameter("id"));
        } catch (NumberFormatException e) {
            // if we didn't pass in an id, we want all books:
            CustomList<Ticket> tickets = TicketService.getAllPendingTickets();
            String json = mapper.writeValueAsString(tickets);
            res.getWriter().print(json);
            //out.print(tickets);
            return;
        }

        // if the catch block didn't trigger, that means we did pass in an id so we can use that to get a specific book:
        CustomList<Ticket> tickets = TicketService.getAllTicketsById(idToGet);
        //out.print(tickets);
        String json = mapper.writeValueAsString(tickets);
        res.getWriter().print(json);


    }
}

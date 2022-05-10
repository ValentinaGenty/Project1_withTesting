package servlets;

import UserDao.*;
import TicketDao.*;
import CustomArrayList.*;
import ConnectionFactory.*;
import Entity.*;
import servlets.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;



public class TestServlet {

    private static UserDao userDao;
    private static TicketDao ticketDao;

    @BeforeEach
    public void setUp(){
        UserDao userDao =UserDaoFactory.getUserDao();
        TicketDao ticketDao = TicketDaoFactory.getTicketDao();
        userDao.initTables();
        ticketDao.fillTables();

        userDao.fillTables();
        ticketDao.fillTables();
    }

    @Test
    public void testGetAllTickets() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(writer);

        new TicketServlet().doGet(request,response);

        writer.flush();

        assertTrue(stringWriter.toString().contains("ticket_system{'ticket_id':3, 'user_id':1, 'reason':'new desk chair', 'amount':200.00,'status':'pending','submitted_date'=2022-05-09}"));

    }
}

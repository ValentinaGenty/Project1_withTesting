package servlets;

import CustomArrayList.CustomList;
import CustomArrayList.CustomArrayList;
import Entity.Ticket;
import Entity.TimeStampWrapper;
import Entity.User;
import TicketDao.TicketDao;
import TicketImpl.TicketDaoImpl;
import UserDao.UserDao;
import UserImpI.UserDaoImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserServlet extends HttpServlet {

    private UserDao userDao = new UserDaoImpl();
    private TicketDao ticketDao = new TicketDaoImpl();


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action="";
        try {
            action=req.getParameter("action");
            if(action==null){
                    resp.getWriter().print("Please add a query to specify your task");
            }
            else if(action.equals("login")){
                ObjectMapper mapper = new ObjectMapper();
                User payload = mapper.readValue(req.getInputStream(), User.class);
                User user=userDao.get(payload);

                if(user==null){
                    resp.getWriter().print("user not authenticated");

                }
                else{
                    if(payload.getPassword().equals(user.getPassword())){
                        resp.getWriter().print("user authenticated");
                    }
                    else{
                        resp.getWriter().print("user not authenticated");

                    }
                }

                resp.setStatus(203);

            }
            else if(action.equals("getAll")) {
                CustomList<User> users = userDao.getAll();
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(users);
                resp.getWriter().print(json);
                resp.setStatus(200);
            }

        }catch (IOException ex){
            resp.setStatus(500);
            resp.getWriter().print("Something went wrong");
            System.out.println(ex.getLocalizedMessage());
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            User payload = mapper.readValue(req.getInputStream(), User.class);
            userDao.create(payload);
            resp.setStatus(203);
            resp.getWriter().print("User successfully added");
        }catch (IOException ex){
            resp.setStatus(500);
            resp.getWriter().print("Something went wrong");
            System.out.println(ex.getLocalizedMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            ObjectMapper mapper = new ObjectMapper();
            User payload = mapper.readValue(req.getInputStream(), User.class);
            userDao.update(payload);
            resp.setStatus(200);
            resp.getWriter().print("Record changed");
        }catch(Exception ex){
            resp.setStatus(500);
            resp.getWriter().print("Problem");
            System.out.println(ex.getLocalizedMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            User payload = mapper.readValue(req.getInputStream(), User.class);
            userDao.delete(payload);
            resp.setStatus((200));
            resp.getWriter().print("Success");
        } catch(Exception ex){
            resp.setStatus(500);
            resp.getWriter().print("Something is wrong");
            System.out.println(ex.getLocalizedMessage());
        }
    }
}

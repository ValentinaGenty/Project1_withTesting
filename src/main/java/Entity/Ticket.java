package Entity;


import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Ticket {
    private int ticket_id;
    private String reason;
    private float amount;
    private String status;
    private int user_id;
    //private java.sql.Date date;
    private Timestamp date;

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Ticket(){};

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

   // public java.sql.Date getDate() {
   //     return date;
   // }

   // public void setDate(java.sql.Date date) {
   //     this.date = date;
   // }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Ticket(int ticket_id, String reason, float amount, String status) {
        this.ticket_id = ticket_id;
        this.reason = reason;
        this.amount = amount;
        this.date = date;
        this.status = status;
    }


    public Ticket(int ticket_id, String reason, float amount, Timestamp date, String status, int user_id) {
        this.ticket_id = ticket_id;
        this.reason = reason;
        this.amount = amount;
        this.date = date;
        this.status = status;
        this.user_id = user_id;
    }

    public Ticket(int ticket_id, String reason) {
        this.ticket_id = ticket_id;
        this.reason = reason;
    }

    public Ticket(int ticket_id, String reason, Timestamp date) {
        this.ticket_id = ticket_id;
        this.reason = reason;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticket_id=" + ticket_id +
                ", reason='" + reason + '\'' +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", date=" + date +
                '}';
    }

    public int getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(int ticket_id) {
        this.ticket_id = ticket_id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

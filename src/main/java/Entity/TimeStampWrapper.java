package Entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class TimeStampWrapper {

    private Timestamp timestamp;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS", timezone = "America/Chicago")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd HH:mm:ss", timezone = "America/Chicago")
    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}

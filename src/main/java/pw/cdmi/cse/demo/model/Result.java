package pw.cdmi.cse.demo.model;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class Result {
    private String message;
    private int status;
    private long timestamp;

    public Result() {}

    public Result(String message, int status) {
        this(message, status, (new Date()).getTime());
    }

    public Result(String message, int status, long timestamp) {
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
    }



    public static Result ok(String message) {
        return new Result(message, HttpStatus.OK.value());
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

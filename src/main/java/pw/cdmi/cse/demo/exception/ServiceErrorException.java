package pw.cdmi.cse.demo.exception;

public class ServiceErrorException extends RuntimeException {
    public ServiceErrorException() {}

    public ServiceErrorException(String message) {
        super(message);
    }
}

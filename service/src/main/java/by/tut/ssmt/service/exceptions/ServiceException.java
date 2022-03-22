package by.tut.ssmt.service.exceptions;

public class ServiceException extends Exception {

    public ServiceException() {
        super();
    }

    public ServiceException(String message, Exception e) {
        super(message, e);
    }

    public ServiceException(Exception e) {
        super(e);
    }

}

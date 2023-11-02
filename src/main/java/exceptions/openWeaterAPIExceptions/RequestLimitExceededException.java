package exceptions.openWeaterAPIExceptions;

import javax.servlet.ServletException;

public class RequestLimitExceededException extends ServletException {
    public RequestLimitExceededException(String message) {
        super(message);
    }
}

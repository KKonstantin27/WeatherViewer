package exceptions.authExceptions;

import javax.servlet.ServletException;

public class SessionExpiredException extends ServletException {
    public SessionExpiredException(String message) {
        super(message);
    }
}

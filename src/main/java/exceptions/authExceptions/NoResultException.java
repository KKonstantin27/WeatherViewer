package exceptions.authExceptions;

import javax.servlet.ServletException;

public class NoResultException extends ServletException {
    public NoResultException(String message) {
        super(message);
    }
}

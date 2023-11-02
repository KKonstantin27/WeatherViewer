package exceptions.openWeaterAPIExceptions;

import javax.servlet.ServletException;

public class InvalidSearchQueryException extends ServletException {
    public InvalidSearchQueryException(String message) {
        super(message);
    }
}

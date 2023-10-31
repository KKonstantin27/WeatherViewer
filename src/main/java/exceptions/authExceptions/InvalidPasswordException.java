package exceptions.authExceptions;

import javax.servlet.ServletException;

public class InvalidPasswordException extends ServletException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}

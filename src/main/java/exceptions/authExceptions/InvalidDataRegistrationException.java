package exceptions.authExceptions;

import javax.servlet.ServletException;

public class InvalidDataRegistrationException extends ServletException {
    public InvalidDataRegistrationException(String message) {
        super(message);
    }
}

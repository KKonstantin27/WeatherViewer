package exceptions.authExceptions;

import javax.servlet.ServletException;

public class UserAlreadyExistException extends ServletException {
    public UserAlreadyExistException(String message) {
        super(message);
    }
}

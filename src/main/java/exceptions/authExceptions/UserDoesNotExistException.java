package exceptions.authExceptions;


import javax.servlet.ServletException;

public class UserDoesNotExistException extends ServletException {
    public UserDoesNotExistException(String message) {
        super(message);
    }
}

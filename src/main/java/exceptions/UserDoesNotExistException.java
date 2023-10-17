package exceptions;

public class UserDoesNotExistException extends AuthorizationException {
    public UserDoesNotExistException(String message) {
        super(message);
    }
}

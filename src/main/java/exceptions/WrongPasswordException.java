package exceptions;

public class WrongPasswordException extends AuthorizationException {
    public WrongPasswordException(String message) {
        super(message);
    }
}

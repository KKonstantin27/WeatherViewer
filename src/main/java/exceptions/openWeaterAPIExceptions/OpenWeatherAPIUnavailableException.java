package exceptions.openWeaterAPIExceptions;

import javax.servlet.ServletException;

public class OpenWeatherAPIUnavailableException extends ServletException {
    public OpenWeatherAPIUnavailableException(String message) {
        super(message);
    }
}

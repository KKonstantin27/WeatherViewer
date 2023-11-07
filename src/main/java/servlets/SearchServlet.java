package servlets;

import dto.LocationDTO;
import exceptions.authExceptions.SessionExpiredException;
import exceptions.openWeaterAPIExceptions.InvalidSearchQueryException;
import exceptions.openWeaterAPIExceptions.NoResultException;
import exceptions.openWeaterAPIExceptions.OpenWeatherAPIUnavailableException;
import exceptions.openWeaterAPIExceptions.RequestLimitExceededException;
import models.UserSession;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/search")
public class SearchServlet extends BaseServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, NoResultException, InvalidSearchQueryException, OpenWeatherAPIUnavailableException, RequestLimitExceededException, SessionExpiredException {
        Cookie[] cookies = request.getCookies();
        String locationName = request.getParameter("location-name-search").replace(' ', '_');
        if (!validator.isValidSearchQuery(locationName)) {
            throw new InvalidSearchQueryException("Некорректные символы в поисковом запросе");
        }
        List<LocationDTO> locations = openWeatherAPIService.searchLocation(locationName);
        WebContext ctx = new WebContext(request, response, getServletContext());
        if (cookies != null) {
            ctx = getCTXForAuthorizeUser(request, response, cookies);
        }
        ctx.setVariable("locations", locations);
        templateEngine.process("search", ctx, response.getWriter());
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, SessionExpiredException {
        Cookie[] cookies = request.getCookies();
        UserSession userSession = userSessionDAO.getBySessionID(cookies[0].getValue());
        String name = request.getParameter("location-name");
        double latitude = Double.parseDouble(request.getParameter("latitude"));
        double longitude = Double.parseDouble(request.getParameter("longitude"));
        locationDAO.save(name, userSession.getUser(), latitude, longitude);
        response.sendRedirect(request.getContextPath() + "/");
    }
}

package servlets;

import dto.WeatherDTO;
import exceptions.authExceptions.SessionExpiredException;
import exceptions.openWeaterAPIExceptions.InvalidSearchQueryException;
import exceptions.openWeaterAPIExceptions.OpenWeatherAPIUnavailableException;
import exceptions.openWeaterAPIExceptions.RequestLimitExceededException;
import models.Location;
import models.UserSession;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value = "")
public class MainPageServlet extends BaseServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, OpenWeatherAPIUnavailableException, InvalidSearchQueryException, RequestLimitExceededException, SessionExpiredException {
        Cookie[] cookies = request.getCookies();
        WebContext ctx = new WebContext(request, response, getServletContext());
        if (cookies != null) {
            ctx = getCTXForAuthorizeUser(request, response, cookies);
            List<Location> locations = locationDAO.getByUser(((UserSession) ctx.getVariable("userSession")).getUser());
            List<WeatherDTO> weatherDTOList = new ArrayList<>();
            if (locations.size() != 0) {
                for (Location location : locations) {
                    weatherDTOList.add(openWeatherAPIService.getWeatherForLocation(location));
                }
                ctx.setVariable("weathers", weatherDTOList);
            }
        }
        templateEngine.process("index", ctx, response.getWriter());
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, SessionExpiredException {
        UserSession userSession = userSessionDAO.getBySessionID(request.getCookies()[0].getValue());
        String locationID = request.getParameter("location-id");
        locationDAO.delete(userSession.getUser(), locationID);
        response.sendRedirect(request.getContextPath() + "/");
    }
}
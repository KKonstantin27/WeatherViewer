package servlets;

import dao.UserDAO;
import dto.WeatherDTO;
import exceptions.authExceptions.SessionExpiredException;
import exceptions.openWeaterAPIExceptions.InvalidSearchQueryException;
import exceptions.openWeaterAPIExceptions.OpenWeatherAPIUnavailableException;
import exceptions.openWeaterAPIExceptions.RequestLimitExceededException;
import models.Location;
import models.User;
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
            User user = userDAO.getByName(cookies[1].getValue()).get();
            List<Location> locations = locationDAO.getByUser(user);
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

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = userDAO.getByName(request.getCookies()[1].getValue()).get();
        String locationID = request.getParameter("location-id");
        locationDAO.delete(user, locationID);
        response.sendRedirect(request.getContextPath() + "/");
    }
}
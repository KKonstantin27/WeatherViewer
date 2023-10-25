package servlets;

import dto.WeatherDTO;
import models.Location;
import models.User;
import models.UserSession;
import org.thymeleaf.context.WebContext;

import java.io.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(value = "")
public class MainPageServlet extends BaseServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        WebContext ctx = new WebContext(request, response, getServletContext());
        if (cookies != null) {
            ctx = getCTXForAuthorizeUser(request, response, cookies);
        }
//        User user = userDAO.getByName(cookies[1].getValue()).get();
//        List<Location> locations = locationDAO.getByUser(user);
//        if (locations.size() != 0) {
//            List<WeatherDTO> weatherDTOList = openWeatherAPIService.getWeatherForLocation();
//            ctx.setVariable("weathers", weatherDTOList);
//        }
        templateEngine.process("index", ctx, response.getWriter());
    }
}
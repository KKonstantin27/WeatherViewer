package servlets;

import models.Location;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/search")
public class SearchServlet extends BaseServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        String locationName = request.getParameter("location-name");
        List<Location> locations = openWeatherAPIService.searchLocation(locationName);
        WebContext ctx = new WebContext(request, response, getServletContext());
        if (cookies != null) {
            ctx = getCTXForAuthorizeUser(request, response, cookies);
        }
        ctx.setVariable("locations", locations);
        templateEngine.process("search", ctx, response.getWriter());
    }
}

package servlets;

import dto.LocationDTO;
import models.Location;
import models.User;
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
        List<LocationDTO> locations = openWeatherAPIService.searchLocation(locationName);
        WebContext ctx = new WebContext(request, response, getServletContext());
        if (cookies != null) {
            ctx = getCTXForAuthorizeUser(request, response, cookies);
        }
        ctx.setVariable("locations", locations);
        templateEngine.process("search", ctx, response.getWriter());
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        User user = userDAO.getByName(cookies[1].getValue()).get();
        String name = request.getParameter("name");
        double latitude = Double.parseDouble(request.getParameter("latitude"));
        double longitude = Double.parseDouble(request.getParameter("longitude"));
        response.sendRedirect(request.getContextPath() + "/");
    }
}

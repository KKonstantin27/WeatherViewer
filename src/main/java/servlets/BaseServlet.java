package servlets;

import dao.LocationDAO;
import dao.UserDAO;
import dao.UserSessionDAO;
import dto.LocationDTO;
import models.User;
import models.UserSession;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import services.OpenWeatherAPIService;
import services.UserService;
import utils.ThymeleafUtil;
import utils.Validator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

public class BaseServlet extends HttpServlet {
    private ThymeleafUtil thymeleafUtil = new ThymeleafUtil();
    protected TemplateEngine templateEngine;
    protected Validator validator = new Validator();
    protected UserService userService = new UserService();
    protected UserDAO userDAO = new UserDAO();
    protected LocationDAO locationDAO = new LocationDAO();
    protected UserSessionDAO userSessionDAO = new UserSessionDAO();
    protected OpenWeatherAPIService openWeatherAPIService = new OpenWeatherAPIService();



    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        templateEngine = thymeleafUtil.getTemplateEngine(getServletContext());
    }

    protected void clearCookies(HttpServletResponse response) {
        Cookie cookieUserSession = new Cookie("userSessionID", "");
        Cookie cookieUserName = new Cookie("userName", "");
        cookieUserSession.setMaxAge(0);
        cookieUserName.setMaxAge(0);
        response.addCookie(cookieUserSession);
        response.addCookie(cookieUserName);
    }
    protected WebContext getCTXForAuthorizeUser(HttpServletRequest request, HttpServletResponse response, Cookie[] cookies) {
        WebContext ctx = new WebContext(request, response, getServletContext());
        String userSessionID = cookies[0].getValue();
        String userName = cookies[1].getValue();
        User user = userDAO.getByName(userName).get();
        Optional<UserSession> userSessionOptional = userSessionDAO.getByUser(user);
        if (userSessionOptional.isPresent() && userSessionOptional.get().getExpiresAt().isAfter(ZonedDateTime.now(ZoneId.of("UTC")))) {
            ctx.setVariable("userSessionID", userSessionID);
            ctx.setVariable("userName", userName);
        } else {
            clearCookies(response);
        }
        return ctx;
    }
}

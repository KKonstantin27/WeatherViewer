package servlets;

import dao.LocationDAO;
import dao.UserDAO;
import dao.UserSessionDAO;
import exceptions.authExceptions.*;
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
import java.io.IOException;
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

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        WebContext ctx = new WebContext(request, response, getServletContext());
        try {
            super.service(request, response);
        } catch (NoResultException | InvalidSearchQueryException e) {
            response.setStatus(401);
            ctx.setVariable("error", e.getMessage());
            templateEngine.process("search", ctx, response.getWriter());
        } catch (UserDoesNotExistException | InvalidPasswordException e) {
            response.setStatus(401);
            ctx.setVariable("authorizationError", e.getMessage());
            templateEngine.process("authorization", ctx, response.getWriter());
        } catch (InvalidDataRegistrationException | UserAlreadyExistException e) {
            response.setStatus(401);
            ctx.setVariable("registrationErrors", e.getMessage().split("_"));
            templateEngine.process("registration", ctx, response.getWriter());
        } catch (Exception e) {
            ctx.setVariable("error", e.getMessage());
        }
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

    protected void clearCookies(HttpServletResponse response) {
        Cookie cookieUserSession = new Cookie("userSessionID", "");
        Cookie cookieUserName = new Cookie("userName", "");
        cookieUserSession.setMaxAge(0);
        cookieUserName.setMaxAge(0);
        response.addCookie(cookieUserSession);
        response.addCookie(cookieUserName);
    }
}

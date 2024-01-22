package servlets;

import dao.LocationDAO;
import dao.UserDAO;
import dao.UserSessionDAO;
import exceptions.authExceptions.*;
import exceptions.openWeaterAPIExceptions.InvalidSearchQueryException;
import exceptions.openWeaterAPIExceptions.NoResultException;
import exceptions.openWeaterAPIExceptions.OpenWeatherAPIUnavailableException;
import exceptions.openWeaterAPIExceptions.RequestLimitExceededException;
import models.UserSession;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import services.AuthService;
import services.OpenWeatherAPIService;
import utils.ThymeleafUtil;
import utils.Validator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BaseServlet extends HttpServlet {
    private ThymeleafUtil thymeleafUtil = new ThymeleafUtil();
    protected TemplateEngine templateEngine;
    protected Validator validator = new Validator();
    protected AuthService authService = new AuthService();
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
            response.setStatus(404);
            ctx.setVariable("error", e.getMessage());
            templateEngine.process("search", ctx, response.getWriter());
        } catch (UserDoesNotExistException | InvalidPasswordException e) {
            response.setStatus(404);
            ctx.setVariable("authorizationError", e.getMessage());
            templateEngine.process("authorization", ctx, response.getWriter());
        } catch (InvalidDataRegistrationException | UserAlreadyExistException e) {
            response.setStatus(400);
            ctx.setVariable("registrationErrors", e.getMessage().split("_"));
            templateEngine.process("registration", ctx, response.getWriter());
        } catch (SessionExpiredException e) {
            response.setStatus(401);
            ctx.setVariable("messageTitle", "Сессия истекла");
            ctx.setVariable("message", e.getMessage());
            clearCookies(response);
            templateEngine.process("message", ctx, response.getWriter());
        } catch (RequestLimitExceededException | OpenWeatherAPIUnavailableException e) {
            response.setStatus(500);
            ctx.setVariable("error", e.getMessage());
            templateEngine.process("errorPage", ctx, response.getWriter());
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    protected WebContext getCTXForAuthorizeUser(HttpServletRequest request, HttpServletResponse response, Cookie[] cookies) throws SessionExpiredException {
        WebContext ctx = new WebContext(request, response, getServletContext());
        String userSessionID = cookies[0].getValue();
        UserSession userSession = userSessionDAO.getBySessionID(userSessionID);
        ctx.setVariable("userSession", userSession);
        ctx.setVariable("userName", userSession.getUser().getName());
        return ctx;
    }

    protected void clearCookies(HttpServletResponse response) {
        Cookie cookieUserSession = new Cookie("userSessionID", "");
        cookieUserSession.setMaxAge(0);
        response.addCookie(cookieUserSession);
    }
}

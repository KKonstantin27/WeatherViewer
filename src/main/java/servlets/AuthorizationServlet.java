package servlets;

import exceptions.authExceptions.SessionExpiredException;
import exceptions.authExceptions.UserDoesNotExistException;
import exceptions.authExceptions.InvalidPasswordException;
import models.User;
import models.UserSession;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(value = "/authorization")
public class AuthorizationServlet extends BaseServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        WebContext ctx = new WebContext(request, response, getServletContext());
        if (cookies != null) {
            response.sendRedirect(request.getContextPath() + "/");
        }
        templateEngine.process("authorization", ctx, response.getWriter());
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, UserDoesNotExistException, InvalidPasswordException, SessionExpiredException {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String userSessionID = authService.signIn(name, password);
        Cookie cookieUserSession = new Cookie("userSessionID", userSessionID);
        Cookie cookieUserName = new Cookie("userName", name);
        cookieUserSession.setMaxAge(-1);
        cookieUserName.setMaxAge(-1);
        response.addCookie(cookieUserSession);
        response.addCookie(cookieUserName);
        response.sendRedirect(request.getContextPath() + "/");
    }
}
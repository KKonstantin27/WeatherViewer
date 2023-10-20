package servlets;

import exceptions.AuthorizationException;
import exceptions.UserDoesNotExistException;
import models.User;
import models.UserSession;
import org.mindrot.jbcrypt.BCrypt;
import org.thymeleaf.context.WebContext;
import services.UserService;

import javax.servlet.ServletException;
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
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        WebContext ctx = new WebContext(request, response, getServletContext());
        try {
            User user = userService.signIn(name, password);
            Optional<UserSession> userSessionOptional = userService.getSession(user);
            UserSession userSession = userSessionOptional.orElseGet(() -> userService.createSession(user));
            Cookie cookieUserSession = new Cookie("userSessionID", String.valueOf(userSession.getId()));
            Cookie cookieUserName = new Cookie("userName", String.valueOf(user.getName()));
            cookieUserSession.setMaxAge(-1);
            cookieUserName.setMaxAge(-1);
            response.addCookie(cookieUserSession);
            response.addCookie(cookieUserName);
            response.sendRedirect(request.getContextPath() + "/");
        } catch (AuthorizationException e) {
            ctx.setVariable("authorizationError", e.getMessage());
            templateEngine.process("authorization", ctx, response.getWriter());
        }
    }
}
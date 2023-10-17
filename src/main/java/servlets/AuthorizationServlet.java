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

@WebServlet(name = "AuthorizationServlet", value = "/authorization")
public class AuthorizationServlet extends BaseServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        WebContext ctx = new WebContext(request, response, getServletContext());
        templateEngine.process("authorization", ctx, response.getWriter());
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        WebContext ctx = new WebContext(request, response, getServletContext());
        try {
            User user = userService.loginUser(name, password);
            UserSession userSession = userService.getSession(user);
            Cookie cookieUserSession = new Cookie("userSessionID", String.valueOf(userSession.getId()));
            Cookie cookieUserName = new Cookie("userName", String.valueOf(user.getName()));
            cookieUserSession.setMaxAge(7200);
            cookieUserName.setMaxAge(7200);
            response.addCookie(cookieUserSession);
            response.addCookie(cookieUserName);
            response.sendRedirect(request.getContextPath() + "/");
        } catch (AuthorizationException e) {
            ctx.setVariable("authorizationError", e.getMessage());
            templateEngine.process("authorization", ctx, response.getWriter());
        }
    }
}
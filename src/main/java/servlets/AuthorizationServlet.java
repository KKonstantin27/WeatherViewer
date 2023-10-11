package servlets;

import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AuthorizationServlet", value = "/authorization")
public class AuthorizationServlet extends BaseServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        WebContext ctx = new WebContext(request, response, getServletContext());
        templateEngine.process("authorization", ctx, response.getWriter());
    }
//    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        List<String> errors = new ArrayList<>();
//
//        String login = request.getParameter("login");
//        String password = request.getParameter("password");
//        String passwordRepeat = request.getParameter("password-repeat");
//
//        errors = validator.validateLogin(login, errors);
//        errors = validator.validatePassword(password, passwordRepeat, errors);
//
//        WebContext ctx = new WebContext(request, response, getServletContext());
//
//        if (errors.size() == 0) {
//            templateEngine.process("index", ctx);
//        } else {
//            ctx.setVariable("errors", errors);
//            templateEngine.process("registration", ctx, response.getWriter());
//        }
//    }
}
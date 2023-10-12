package servlets;

import org.mindrot.jbcrypt.BCrypt;
import org.thymeleaf.context.WebContext;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "RegistrationServlet", value = "/registration")
public class RegistrationServlet extends BaseServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        WebContext ctx = new WebContext(request, response, getServletContext());
        templateEngine.process("registration", ctx, response.getWriter());
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<String> errors = new ArrayList<>();

        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String passwordRepeat = request.getParameter("password-repeat");

        errors = validator.validateLogin(name, errors);
        errors = validator.validatePassword(password, passwordRepeat, errors);

        WebContext ctx = new WebContext(request, response, getServletContext());

        if (errors.size() == 0) {
            templateEngine.process("index", ctx);
            userDAO.save(name, BCrypt.hashpw(password, BCrypt.gensalt()));
            ctx.setVariable("successfulRegistration", "Регистрация успешно завершена, теперь Вы можете войти в аккаунт, используя учётные данные");
            templateEngine.process("authorization", ctx, response.getWriter());
        } else {
            ctx.setVariable("errors", errors);
            templateEngine.process("registration", ctx, response.getWriter());
        }
    }
}
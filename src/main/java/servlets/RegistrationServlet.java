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
        List<String> messages = new ArrayList<>();

        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String passwordRepeat = request.getParameter("password-repeat");

        messages = validator.validateLogin(name, messages);
        messages = validator.validatePassword(password, passwordRepeat, messages);

        WebContext ctx = new WebContext(request, response, getServletContext());

        if (messages.size() == 0) {
            templateEngine.process("index", ctx);
            userDAO.save(name, BCrypt.hashpw(password, BCrypt.gensalt()));
            ctx.setVariable("successfulRegistrationMessage", "Регистрация успешно завершена, теперь Вы можете войти в аккаунт, используя свои учётные данные");
            templateEngine.process("successfulRegistration", ctx, response.getWriter());
        } else {
            ctx.setVariable("errorRegistrationMessages", messages);
            templateEngine.process("registration", ctx, response.getWriter());
        }
    }
}
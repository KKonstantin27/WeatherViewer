package servlets;

import org.thymeleaf.context.WebContext;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "RegistrationServlet", value = "")
public class RegistrationServlet extends BaseServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        WebContext ctx = new WebContext(request, response, getServletContext());
        templateEngine.process("registration", ctx, response.getWriter());
    }

}
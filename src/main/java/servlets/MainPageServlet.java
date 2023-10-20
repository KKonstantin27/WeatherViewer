package servlets;

import models.User;
import models.UserSession;
import org.thymeleaf.context.WebContext;

import java.io.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(value = "")
public class MainPageServlet extends BaseServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        WebContext ctx = new WebContext(request, response, getServletContext());
        if (cookies != null) {
            ctx = getCTXForAuthorizeUser(request, response, cookies);
        }
        templateEngine.process("index", ctx, response.getWriter());
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }
}
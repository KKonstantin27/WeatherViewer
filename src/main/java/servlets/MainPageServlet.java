package servlets;

import org.thymeleaf.context.WebContext;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(value = "")
public class MainPageServlet extends BaseServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        WebContext ctx = new WebContext(request, response, getServletContext());
        Cookie[] cookies = request.getCookies();
        String userSessionID = null;
        String userName = null;
        if (cookies != null) {
            userSessionID = cookies[0].getValue();
            userName = cookies[1].getValue();
        }
        ctx.setVariable("userSessionID", userSessionID);
        ctx.setVariable("userName", userName);
        templateEngine.process("index", ctx, response.getWriter());
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }
}
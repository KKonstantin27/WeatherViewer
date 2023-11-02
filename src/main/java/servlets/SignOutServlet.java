package servlets;

import models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/signOut")
public class SignOutServlet extends BaseServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String userSessionID = request.getCookies()[0].getValue();
        User user = userDAO.getByName(request.getCookies()[1].getValue()).get();
        authService.signOut(user, userSessionID);
        clearCookies(response);
        response.sendRedirect(request.getContextPath() + "/");
    }
}

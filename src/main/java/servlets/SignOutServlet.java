package servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/signOut")
public class SignOutServlet extends BaseServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userSessionID = request.getCookies()[0].getValue();
        authService.signOut(userSessionID);
        clearCookies(response);
        response.sendRedirect(request.getContextPath() + "/");
    }
}

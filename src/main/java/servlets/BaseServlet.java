package servlets;

import org.thymeleaf.TemplateEngine;
import services.UserService;
import utils.ThymeleafUtil;
import utils.Validator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class BaseServlet extends HttpServlet {
    private ThymeleafUtil thymeleafUtil = new ThymeleafUtil();
    protected TemplateEngine templateEngine;
    protected Validator validator = new Validator();
    protected UserService userService = new UserService();


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        templateEngine = thymeleafUtil.getTemplateEngine(getServletContext());
    }
}

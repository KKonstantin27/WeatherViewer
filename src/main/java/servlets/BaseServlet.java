package servlets;

import org.thymeleaf.TemplateEngine;
import utils.ThymeleafUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class BaseServlet extends HttpServlet {
    private ThymeleafUtil thymeleafUtil = new ThymeleafUtil();
    protected TemplateEngine templateEngine;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        templateEngine = thymeleafUtil.getTemplateEngine(getServletContext());
    }
}

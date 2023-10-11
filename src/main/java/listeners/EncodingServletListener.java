package listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class EncodingServletListener implements ServletContextListener {
    @Override
    public void contextInitialized (ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();
        sc.setRequestCharacterEncoding("UTF-8");
        sc.setResponseCharacterEncoding("UTF-8");
    }
}

package utils;

import dao.UserSessionDAO;
import models.Location;
import models.UserSession;
import models.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DBUtil {
    private static SessionFactory sessionFactory;

    public static void buildSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            configuration.configure().addAnnotatedClass(User.class).addAnnotatedClass(Location.class).addAnnotatedClass(UserSession.class);
            sessionFactory = configuration.buildSessionFactory();
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    public void clearOldSessions() {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            UserSessionDAO userSessionDAO = new UserSessionDAO();
            userSessionDAO.delete();
        };
        pool.scheduleAtFixedRate(task, 0, 8, TimeUnit.HOURS);
    }
}

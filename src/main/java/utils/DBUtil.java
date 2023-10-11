package utils;

import models.Location;
import models.Session;
import models.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DBUtil {
    private static SessionFactory sessionFactory;

    private static void createSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure().addAnnotatedClass(User.class).addAnnotatedClass(Location.class).addAnnotatedClass(Session.class);
        sessionFactory = configuration.buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            createSessionFactory();
        }
        return sessionFactory;
    }
}

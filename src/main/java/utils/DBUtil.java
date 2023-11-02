package utils;

import lombok.Data;
import models.Location;
import models.User;
import models.UserSession;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@Data
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

    public static void setSessionFactory(SessionFactory sessionFactory) {
        DBUtil.sessionFactory = sessionFactory;
    }
}

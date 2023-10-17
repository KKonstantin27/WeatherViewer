package dao;

import models.User;
import models.UserSession;
import org.hibernate.Session;
import org.hibernate.query.Query;
import utils.DBUtil;

import java.util.Optional;

public class UserSessionDAO {

    public Optional<UserSession> getByUser(User user) {
        try (Session session = DBUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("FROM UserSession WHERE user = :user");
            query.setParameter("user", user);
            Optional<UserSession> userSessionOptional = query.uniqueResultOptional();
            session.getTransaction().commit();
            return userSessionOptional;
        }
    }
    public UserSession save(User user) {
        UserSession userSession;
        try (Session session = DBUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            userSession = new UserSession(user);
            session.persist(userSession);
            session.getTransaction().commit();
        }
        return userSession;
    }
}

package dao;

import models.User;
import models.UserSession;
import org.hibernate.Session;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;
import utils.DBUtil;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

public class UserSessionDAO {

    public Optional<UserSession> getByUser(User user) {
        try (Session session = DBUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query<UserSession> query = session.createQuery("FROM UserSession WHERE user = :user", UserSession.class);
            query.setParameter("user", user);
            Optional<UserSession> userSessionOptional = query.uniqueResultOptional();
            userSessionOptional.ifPresent(UserSession::updateExpiresAt);
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

    public void delete(User user) {
        try (Session session = DBUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            MutationQuery query = session.createMutationQuery("DELETE FROM UserSession WHERE user = :user");
            query.setParameter("user", user);
            query.executeUpdate();
            session.getTransaction().commit();
        }
    }
    public void delete() {
        try (Session session = DBUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            MutationQuery query = session.createMutationQuery("DELETE FROM UserSession WHERE expires_at < :currentDateTime");
            query.setParameter("currentDateTime", ZonedDateTime.now(ZoneId.of("UTC")));
            query.executeUpdate();
            session.getTransaction().commit();
        }
    }
}

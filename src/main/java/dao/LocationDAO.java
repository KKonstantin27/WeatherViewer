package dao;

import models.Location;
import models.User;
import models.UserSession;
import org.hibernate.Session;
import org.hibernate.query.Query;
import utils.DBUtil;

import java.util.List;
import java.util.Optional;

public class LocationDAO {
    public void save(String name, User user, double latitude, double longitude) {
        try (Session session = DBUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Location location = new Location(name, user, latitude, longitude);
            session.persist(location);
            session.getTransaction().commit();
        }
    }

    public List<Location> getByUser(User user) {
        try (Session session = DBUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query<Location> query = session.createQuery("FROM Location WHERE user = :user", Location.class);
            query.setParameter("user", user);
            List<Location> locations = query.getResultList();
            session.getTransaction().commit();
            return locations;
        }
    }
}

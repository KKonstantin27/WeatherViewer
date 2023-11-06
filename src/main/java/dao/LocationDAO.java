package dao;

import models.Location;
import models.User;
import org.hibernate.Session;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;
import utils.DBUtil;

import java.util.List;

public class LocationDAO {
    public void save(String locationName, User user, double latitude, double longitude) {
        try (Session session = DBUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Location location = new Location(locationName, user, latitude, longitude);
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

    public void delete(User user, String locationID) {
        try (Session session = DBUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            MutationQuery query = session.createMutationQuery("DELETE FROM Location WHERE user = :user AND id = :locationID");
            query.setParameter("user", user);
            query.setParameter("locationID", locationID);
            query.executeUpdate();
            session.getTransaction().commit();
        }
    }
}

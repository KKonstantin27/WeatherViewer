package dao;

import models.User;
import org.hibernate.Session;
import utils.DBUtil;

public class UserDAO {
    public void save(String name, String password) {
        try (Session session = DBUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            User user = new User(name, password);
            session.persist(user);
            session.getTransaction().commit();
        }
    }
}

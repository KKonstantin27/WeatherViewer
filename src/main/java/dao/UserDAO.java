package dao;

import exceptions.UserAlreadyExistException;
import models.User;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import utils.DBUtil;

import java.util.Optional;

public class UserDAO {
    public void save(String name, String password) throws UserAlreadyExistException {
        try (Session session = DBUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            User user = new User(name, password);
            session.persist(user);
            session.getTransaction().commit();
        } catch (ConstraintViolationException e) {
            throw new UserAlreadyExistException("Имя пользователя занято");
        }
    }

    public Optional<User> getByName(String name) {
        try (Session session = DBUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("FROM User WHERE name = :name");
            query.setParameter("name", name);
            Optional<User> userOptional = query.uniqueResultOptional();
            session.getTransaction().commit();
            return userOptional;
        }
    }
}

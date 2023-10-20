package services;

import dao.UserDAO;
import dao.UserSessionDAO;
import exceptions.AuthorizationException;
import exceptions.UserAlreadyExistException;
import exceptions.UserDoesNotExistException;
import exceptions.WrongPasswordException;
import models.User;
import models.UserSession;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

public class UserService {
    private UserDAO userDAO = new UserDAO();
    private UserSessionDAO userSessionDAO = new UserSessionDAO();
    public void signUp(String name, String password) throws UserAlreadyExistException {
        userDAO.save(name, BCrypt.hashpw(password, BCrypt.gensalt()));
    }

    public User signIn(String name, String password) throws AuthorizationException {
        Optional<User> userOptional = userDAO.getByName(name);
        User user = userOptional.orElseThrow(() -> new UserDoesNotExistException("Пользователь не найден"));
        if (!BCrypt.checkpw(password, user.getPassword())) {
            throw new WrongPasswordException("Неверный пароль");
        }
        return user;
    }

    public Optional<UserSession> getSession(User user) {
        Optional<UserSession> userSessionOptional = userSessionDAO.getByUser(user);
        return userSessionOptional;
    }

    public UserSession createSession(User user) {
        return userSessionDAO.save(user);
    }

    public void signOut(User user) {
        userSessionDAO.delete(user);
    }
}

package services;

import dao.UserDAO;
import dao.UserSessionDAO;
import exceptions.authExceptions.InvalidPasswordException;
import exceptions.authExceptions.UserAlreadyExistException;
import exceptions.authExceptions.UserDoesNotExistException;
import models.User;
import models.UserSession;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

public class AuthService {
    private UserDAO userDAO = new UserDAO();
    private UserSessionDAO userSessionDAO = new UserSessionDAO();

    public void signUp(String name, String password) throws UserAlreadyExistException {
        userDAO.save(name, BCrypt.hashpw(password, BCrypt.gensalt()));
    }

    public String signIn(String name, String password) throws InvalidPasswordException, UserDoesNotExistException {
        Optional<User> userOptional = userDAO.getByName(name);
        User user = userOptional.orElseThrow(() -> new UserDoesNotExistException("Пользователь не найден"));
        if (!BCrypt.checkpw(password, user.getPassword())) {
            throw new InvalidPasswordException("Неверный пароль");
        }
        UserSession userSession = userSessionDAO.save(user);
        return userSession.getId();
    }

    public void signOut(String userSessionID) {
        userSessionDAO.delete(userSessionID);
    }
}

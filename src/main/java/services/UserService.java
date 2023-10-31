package services;

import dao.UserDAO;
import dao.UserSessionDAO;
import exceptions.authExceptions.UserAlreadyExistException;
import exceptions.authExceptions.UserDoesNotExistException;
import exceptions.authExceptions.InvalidPasswordException;
import models.User;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

public class UserService {
    private UserDAO userDAO = new UserDAO();
    private UserSessionDAO userSessionDAO = new UserSessionDAO();

    public void signUp(String name, String password) throws UserAlreadyExistException {
        userDAO.save(name, BCrypt.hashpw(password, BCrypt.gensalt()));
    }

    public User signIn(String name, String password) throws InvalidPasswordException, UserDoesNotExistException {
        Optional<User> userOptional = userDAO.getByName(name);
        User user = userOptional.orElseThrow(() -> new UserDoesNotExistException("Пользователь не найден"));
        if (!BCrypt.checkpw(password, user.getPassword())) {
            throw new InvalidPasswordException("Неверный пароль");
        }
        return user;
    }

    public void signOut(User user, String userSessionID) {
        userSessionDAO.delete(user, userSessionID);
    }
}

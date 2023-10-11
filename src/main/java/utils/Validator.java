package utils;

import java.util.List;

public class Validator {
    private String loginInvalidChar = "Логин содержит недопустимые символы";
    private String loginInvalidLength = "Длина логина должна быть от 4 до 20 символов";
    private String passwordInvalidChar = "Пароль содержит недопустимые символы";
    private String passwordInvalidLength = "Длина пароля должна быть от 8 до 20 символов";
    private String differentPasswords = "Введённые пароли не совпадают";
    public List<String> validateLogin(String login, List<String> errors) {
        if (login.matches("^[a-zA-Z\\d._-]*$")) {
            errors.add(loginInvalidChar);
        }
        if (login.length() > 20 || login.length() < 4) {
            errors.add(loginInvalidLength);
        }
        return errors;
    }

    public List<String> validatePassword(String password, String passwordRepeat, List<String> errors) {
        if (password.matches("^\\S*$")) {
            errors.add(passwordInvalidChar);
        }
        if (password.length() > 20 || password.length() < 8) {
            errors.add(passwordInvalidLength);
        }
        if (!password.equals(passwordRepeat)) {
            errors.add(differentPasswords);
        }
        return errors;
    }
}

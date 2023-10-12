package utils;

import java.util.List;

public class Validator {
    private String loginInvalidChar = "Логин содержит недопустимые символы";
    private String loginInvalidLength = "Логин должен быть от 4 до 20 символов";
    private String passwordInvalidChar = "Пароль содержит недопустимые символы";
    private String passwordInvalidLength = "Пароль должен быть от 8 до 20 символов";
    private String differentPasswords = "Введённые пароли не совпадают";

    public List<String> validateLogin(String name, List<String> errors) {
        if (name.matches("^[a-zA-Z\\d._-]*$")) {
            errors.add(loginInvalidChar);
        }
        if (name.length() > 20 || name.length() < 4) {
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

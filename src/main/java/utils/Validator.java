package utils;

import java.util.List;

public class Validator {
    private String loginInvalidChar = "Логин содержит недопустимые символы";
    private String loginInvalidLength = "Логин должен быть от 4 до 20 символов";
    private String passwordInvalidChar = "Пароль содержит недопустимые символы";
    private String passwordInvalidLength = "Пароль должен быть от 8 до 20 символов";
    private String differentPasswords = "Введённые пароли не совпадают";

    public List<String> validateLogin(String name, List<String> messages) {
        if (!name.matches("^(?!.*\\s)[a-zA-Z\\d._-]*$")) {
            messages.add(loginInvalidChar);
        }
        if (name.length() > 20 || name.length() < 4) {
            messages.add(loginInvalidLength);
        }
        return messages;
    }

    public List<String> validatePassword(String password, String passwordRepeat, List<String> messages) {
        if (!password.matches("^(?!.*\\s)[a-zA-Z\\d!@#$%^&*()_=+;:?.,<>]*$")) {
            messages.add(passwordInvalidChar);
        }
        if (password.length() > 20 || password.length() < 8) {
            messages.add(passwordInvalidLength);
        }
        if (!password.equals(passwordRepeat)) {
            messages.add(differentPasswords);
        }
        return messages;
    }
}

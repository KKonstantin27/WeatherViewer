package utils;

public class Validator {
    private String loginInvalidChar = "Логин содержит недопустимые символы_";
    private String loginInvalidLength = "Логин должен быть от 4 до 20 символов_";
    private String passwordInvalidChar = "Пароль содержит недопустимые символы_";
    private String passwordInvalidLength = "Пароль должен быть от 8 до 20 символов_";
    private String differentPasswords = "Введённые пароли не совпадают_";

    public StringBuilder validateLogin(String name, StringBuilder errors) {
        if (!name.matches("^(?!.*\\s)[a-zA-Z\\d._-]*$")) {
            errors.append(loginInvalidChar);
        }
        if (name.length() > 20 || name.length() < 4) {
            errors.append(loginInvalidLength);
        }
        return errors;
    }

    public StringBuilder validatePassword(String password, String passwordRepeat, StringBuilder errors) {
        if (!password.matches("^(?!.*\\s)[a-zA-Z\\d!@#$%^&*()_=+;:?.,<>]*$")) {
            errors.append(passwordInvalidChar);
        }
        if (password.length() > 20 || password.length() < 8) {
            errors.append(passwordInvalidLength);
        }
        if (!password.equals(passwordRepeat)) {
            errors.append(differentPasswords);
        }
        return errors;
    }

    public boolean isValidSearchQuery(String locationName) {
        return locationName.matches("^[\\p{L}\\-_]+$");
    }
}

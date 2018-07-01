package ru.game.validator;

import ru.game.dao.UserDao;
import ru.game.util.PasswordCryptUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public final class AuthValidator {
    public static List<String> validateLogin(String name, String password, UserDao userDao) {
        var result = new ArrayList<String>();
        if (name.isEmpty() || password.isEmpty()) {
            result.add("Please fill in all fields");
            return result;
        }
        if (!Pattern.matches("^[a-zA-Z0-9]*$", name)) result.add("Username must contains only characters and numbers");
        if (result.isEmpty()) {
            var user = userDao.findUserByUsername(name);
            if (user == null) {
                result.add("No user with such username");
            } else if (!PasswordCryptUtil.checkPassword(password, user.getPassword())) {
                result.add("Wrong password");
            }
        }
        return result;
    }

    public static boolean validateCookie(String usernameCookie, String tokenCookie, UserDao userDao) {
        var user = userDao.findUserByUsername(usernameCookie);
        if (user != null)
            return user.getToken().equals(tokenCookie);
        return false;
    }

    public static List<String> validateRegistration(String username, String password, String confirm, UserDao userDao) {
        var result = new ArrayList<String>();
        if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            result.add("Please fill in all fields");
            return result;
        }
        if (!Pattern.matches("^[a-zA-Z0-9]*$", username))
            result.add("Username must contains only characters and numbers");
        if (username.length() < 6 || username.length() > 32)
            result.add("Username must be longer than 6 symbols and shorter than 32");
        if (password.length() < 6 || password.length() > 32)
            result.add("Username must be longer than 6 symbols and shorter than 32");
        if (!password.equals(confirm)) result.add("Passwords do not confirm");
        if (result.isEmpty()) {
            var user = userDao.findUserByUsername(username);
            if (user != null) {
                result.add("User with this username is already registered");
            }
        }
        return result;
    }
}

package ru.game.service;

import ru.game.dao.UserDao;
import ru.game.dao.UserDaoImpl;
import ru.game.util.PasswordCryptUtil;
import ru.game.validator.AuthValidator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.SecureRandom;
import java.util.Base64;

public class UserServiceImpl implements UserService {
    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void registerUser(String username, String password) {
        password = PasswordCryptUtil.hashPassword(password);
        userDao.createUser(username, password);
    }

    @Override
    public String authByCookies(Cookie[] cookies) {
        String usernameCookie = null;
        String tokenCookie = null;
        for (var c : cookies) {
            if (c.getName().equals("username"))
                usernameCookie = c.getValue();
            if (c.getName().equals("token"))
                tokenCookie = c.getValue();
            if (usernameCookie != null && tokenCookie != null) break;
        }
        if (usernameCookie != null && tokenCookie != null) {
            boolean ok = AuthValidator.validateCookie(usernameCookie, tokenCookie, userDao);
            if (ok) return usernameCookie;
        }
        return null;
    }

    @Override
    public String saveCookies(String username) {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[48];
        random.nextBytes(bytes);
        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
        String token = encoder.encodeToString(bytes);
        if(!userDao.addCookieToken(username, token)){
            token = null;
        }
        return token;
    }

    @Override
    public void invalidCookies(String username) {
        userDao.invalidateCookieToken(username);
    }

    @Override
    public void sendCookies(String rememberMe, HttpServletResponse resp, String username) {
        if (rememberMe != null && !rememberMe.isEmpty()) {
            var usernameCookie = new Cookie("username", username);
            var tokenCookie = new Cookie("token", saveCookies(username));
            resp.addCookie(usernameCookie);
            resp.addCookie(tokenCookie);
        }
    }
}

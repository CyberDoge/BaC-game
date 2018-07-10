package ru.game.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public interface UserService {
    void registerUser(String username, String password);

    String authByCookies(Cookie[] cookies);

    String saveCookies(String username);

    void invalidCookies(String username);

    void sendCookies(String checkBox, HttpServletResponse response, String username);
}

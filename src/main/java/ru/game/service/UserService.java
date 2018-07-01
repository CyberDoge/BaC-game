package ru.game.service;

import javax.servlet.http.Cookie;

public interface UserService {
    void registerUser(String username, String password);
    String authByCookies(Cookie[] cookies);
    String saveCookies(String username);
    void invalidCookies(String username);
}

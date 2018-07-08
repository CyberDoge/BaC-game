package ru.game.dao;

import ru.game.entity.User;

public interface UserDao {
    User findUserByUsername(String username);

    boolean addCookieToken(String username, String token);

    User createUser(String username, String password);

    void invalidateCookieToken(String username);

}

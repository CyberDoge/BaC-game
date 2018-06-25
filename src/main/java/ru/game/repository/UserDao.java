package ru.game.repository;

import ru.game.entity.User;

public interface UserDao {
    User findUserByUsername(String username);

    User createUser(String username, String password);
}

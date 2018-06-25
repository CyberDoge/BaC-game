package ru.game.service;

import ru.game.entity.Game;
import ru.game.entity.User;

import java.util.List;

public interface UserService {
    User loginUser(String username, String password);

    User createUser(String username, String password);

    boolean containsUser(String username);
}

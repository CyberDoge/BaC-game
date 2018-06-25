package ru.game.service;

import ru.game.entity.Game;
import ru.game.entity.User;
import ru.game.repository.UserDao;
import ru.game.repository.UserDaoImpl;
import ru.game.util.PasswordCryptUtil;

import java.util.List;


public class UserServiceImpl implements UserService {
    private UserDao userDao;

    public UserServiceImpl() {
        userDao = new UserDaoImpl();
    }

    @Override
    public User loginUser(String username, String password) {
        User user = userDao.findUserByUsername(username);
        if ((user == null) || !PasswordCryptUtil.checkPassword(password, user.getPassword())) return null;
        return user;
    }


    @Override
    public User createUser(String username, String password) {
        return userDao.createUser(username, password);
    }

    @Override
    public boolean containsUser(String username) {
        if (userDao.findUserByUsername(username) != null) return true;
        return false;
    }
}

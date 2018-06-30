package ru.game.repository;

import ru.game.entity.User;
import ru.game.util.DbUtil;
import ru.game.util.PasswordCryptUtil;

import java.sql.*;


public class UserDaoImpl implements UserDao {
    private Connection connection;

    public UserDaoImpl() {
        connection = DbUtil.getConnection();
    }

    public User findUserByUsername(String username) {
        User user = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE username=?;");
            preparedStatement.setString(1, username);
            ResultSet result = preparedStatement.executeQuery();
            if (!result.next()) return null;
            user = new User(result.getInt(1), result.getString(2), result.getString(3));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public User createUser(String username, String password) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user (username, password)  VALUES(?, ?);", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, username);
            password = PasswordCryptUtil.hashPassword(password);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
            var resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int id = resultSet.getInt(1);
            return new User(id, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
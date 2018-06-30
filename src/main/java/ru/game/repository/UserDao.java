package ru.game.repository;

import ru.game.entity.User;
import ru.game.util.DbUtil;
import ru.game.util.PasswordCryptUtil;

import java.sql.*;


public class UserDao {
    private Connection connection;

    public UserDao() {
        connection = DbUtil.getConnection();
    }

    public User findUserByUsername(String username) {
        User user = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        try {
            connection = DbUtil.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE username=?;");
            preparedStatement.setString(1, username);
            result = preparedStatement.executeQuery();
            if (!result.next()) return null;
            user = new User(result.getInt(1), result.getString(2), result.getString(3));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(preparedStatement, result);
        }
        return user;
    }

    public User createUser(String username, String password) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO user (username, password)  VALUES(?, ?);",
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, username);
            password = PasswordCryptUtil.hashPassword(password);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int id = resultSet.getInt(1);
            return new User(id, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(preparedStatement, resultSet);
        }
        return null;
    }

    private void close(PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if (preparedStatement != null) preparedStatement.close();
            if (resultSet != null) resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
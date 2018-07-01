package ru.game.dao;

import ru.game.entity.User;
import ru.game.util.DbUtil;
import ru.game.util.PasswordCryptUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;


public class UserDao {

    private static final String USER_BY_USERNAME_QUERY = "SELECT * FROM user WHERE username=?";

    private static final String CREATE_USER = "INSERT INTO user (username, password)  VALUES(?, ?);";

    private static final String SAVE_COOKIES = "UPDATE user SET token=? where username =?";

    public User findUserByUsername(String username) {
        User user = null;
        ResultSet result = null;
        try (var preparedStatement = DbUtil.getConnection().prepareStatement(USER_BY_USERNAME_QUERY)) {
            preparedStatement.setString(1, username);
            result = preparedStatement.executeQuery();
            if (!result.next()) return null;
            user = new User(result.getInt(1), result.getString(2), result.getString(3), result.getString(4));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) result.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    public void addCookieToken(String username, String token) {
        try (var preparedStatement = DbUtil.getConnection().prepareStatement(SAVE_COOKIES)) {
            preparedStatement.setString(1, token);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User createUser(String username, String password) {
        ResultSet resultSet = null;
        try (var preparedStatement = DbUtil.getConnection().prepareStatement(CREATE_USER, Statement.RETURN_GENERATED_KEYS)) {
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
            try {
                if (resultSet != null) resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void invalidCookieToken(String username){
        try (var preparedStatement = DbUtil.getConnection().prepareStatement(SAVE_COOKIES)) {
            preparedStatement.setNull(1, Types.VARCHAR);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
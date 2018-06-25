package ru.game.repository;

import ru.game.util.DbUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

public class GameDaoImpl implements GameDao {
    private Connection connection;

    public GameDaoImpl() {
        connection = DbUtil.getConnection();
    }

    @Override
    public int createGame(int userId, int secretNum) {
        int id = -1;
        try {
            Statement statement = connection.createStatement();
            var query = String.format("INSERT INTO game (user_id, secret_num) VALUES (%d, %d);", userId, secretNum);
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            var resultSet = statement.getGeneratedKeys();
            resultSet.next();
            id = resultSet.getInt(1);
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public void saveAttempt(int gameId, int num, int attempt, Date date) {
        try {
            var statement = connection.prepareStatement("INSERT INTO attempts(game_id, num, attempt, datetime) VALUES (?,?,?,?)");
            statement.setInt(1, gameId);
            statement.setInt(2, num);
            statement.setInt(3, attempt);
            statement.setTimestamp(4, new Timestamp(date.getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void finishGame(int gameId) {
        try {
            var statement = connection.prepareStatement("UPDATE game SET leaved = 0 WHERE game_id = ?;");
            statement.setInt(1, gameId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

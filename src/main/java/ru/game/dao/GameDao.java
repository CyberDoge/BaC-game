package ru.game.dao;

import ru.game.util.DbUtil;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

public class GameDao {

    private static final String SAVE_ATTEMPT = "INSERT INTO attempts(game_id, num, attempt, datetime) VALUES (?,?,?,?)";
    private static final String FINISH_GAME = "UPDATE game SET leaved = 0 WHERE game_id = ?";
    private static final String CREATE_GAME = "INSERT INTO game (user_id, secret_num) VALUES (?,?);";

    public int createGame(int userId, int secretNum) {
        int id = -1;
        try (PreparedStatement statement = DbUtil.getConnection().prepareStatement(CREATE_GAME, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, userId);
            statement.setInt(2, secretNum);
            statement.executeUpdate();
            var resultSet = statement.getGeneratedKeys();
            resultSet.next();
            id = resultSet.getInt(1);
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public void saveAttempt(int gameId, int num, int attempt, Date date) {
        try (
                var connection = DbUtil.getConnection();
                var statement = connection.prepareStatement(SAVE_ATTEMPT)
        ) {
            statement.setInt(1, gameId);
            statement.setInt(2, num);
            statement.setInt(3, attempt);
            statement.setTimestamp(4, new Timestamp(date.getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void finishGame(int gameId) {
        try (
                var connection = DbUtil.getConnection();
                var statement = connection.prepareStatement(FINISH_GAME)
        ) {
            statement.setInt(1, gameId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

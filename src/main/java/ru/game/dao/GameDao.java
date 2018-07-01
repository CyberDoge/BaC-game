package ru.game.dao;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.game.util.DbUtil;

import java.sql.*;
import java.util.Date;

public class GameDao {

    private static final String SAVE_ATTEMPT = "INSERT INTO attempts(game_id, num, attempt, datetime) VALUES (?,?,?,?)";
    private static final String FINISH_GAME = "UPDATE game SET leaved = 0 WHERE game_id = ?";
    private static final String CREATE_GAME = "INSERT INTO game (user_id, secret_num) VALUES (?,?);";

    private static final Logger LOGGER = LogManager.getLogger(GameDao.class.getName());

    public int createGame(int userId, int secretNum) {
        int id = -1;
        ResultSet resultSet = null;
        try (var con = DbUtil.getConnection();
             PreparedStatement statement = con.prepareStatement(CREATE_GAME, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, userId);
            statement.setInt(2, secretNum);
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            id = resultSet.getInt(1);
            return id;
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Creating game SQL-exception: ", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return id;
    }

    public void saveAttempt(int gameId, int num, int attempt, Date date) {
        try (var con = DbUtil.getConnection();
             PreparedStatement statement = con.prepareStatement(SAVE_ATTEMPT)) {
            statement.setInt(1, gameId);
            statement.setInt(2, num);
            statement.setInt(3, attempt);
            statement.setTimestamp(4, new Timestamp(date.getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Save attempt SQL-exception: ", e);
        }
    }

    public void finishGame(int gameId) {
        try (var con = DbUtil.getConnection();
             PreparedStatement statement = con.prepareStatement(FINISH_GAME)) {
            statement.setInt(1, gameId);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Finish game SQL-exception: ", e);
        }
    }
}

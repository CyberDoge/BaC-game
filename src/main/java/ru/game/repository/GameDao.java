package ru.game.repository;

import java.util.Date;

public interface GameDao {
    int createGame(int userId, int secretNum);

    void saveAttempt(int gameId, int num, int attempt, Date date);
    void finishGame(int gameId);
}

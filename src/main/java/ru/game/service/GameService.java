package ru.game.service;

import ru.game.entity.Game;
import ru.game.dao.GameDao;

public interface GameService {
    int createGame(int userId, GameDao gameDao);

    Game.Response createResponse(String text, GameDao gameDao);
}

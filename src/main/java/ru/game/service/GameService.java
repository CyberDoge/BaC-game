package ru.game.service;

import ru.game.dao.GameDao;
import ru.game.dao.GameDaoImpl;
import ru.game.entity.game.Response;

public interface GameService {
    int createGame(int userId, GameDao gameDao);

    Response createResponse(String text, GameDao gameDao);
}

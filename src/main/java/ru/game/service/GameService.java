package ru.game.service;

import ru.game.entity.Game;

public interface GameService {
    int createGame(int userId);

    Game.Response createResponse(String text);
}

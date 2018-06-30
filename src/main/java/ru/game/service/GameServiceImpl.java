package ru.game.service;

import ru.game.entity.Game;
import ru.game.dao.GameDao;

import java.util.Date;

public class GameServiceImpl implements GameService {
    private Game game;


    @Override
    public int createGame(int userId, GameDao gameDao) {
        game = new Game();
        var id = gameDao.createGame(userId, Integer.parseInt(game.getSecretNum()));
        game.setGameId(id);
        return id;
    }

    @Override
    public Game.Response createResponse(String userAttempt, GameDao gameDao) throws NumberFormatException {
        var result = game.newResponse(userAttempt);
        char[] m = userAttempt.toCharArray();
        for (int i = 0; i < m.length; i++) {
            if (game.getSecretNum().contains(Character.toString(m[i]))) {
                if (game.getSecretNum().charAt(i) == m[i])
                    result.incrementBulls();
                else
                    result.incrementCows();
            }
        }
        result.setResult(result.getBulls() + "B" + result.getCows() + "C");
        gameDao.saveAttempt(game.getGameId(), game.incrementAttemptNumber(), Integer.parseInt(userAttempt), new Date());
        if (result.getBulls() == 4) {
            result.setEnd(true);
            endGame(gameDao);
        }
        return result;
    }

    private void endGame(GameDao gameDao) {
        gameDao.finishGame(game.getGameId());
    }
}

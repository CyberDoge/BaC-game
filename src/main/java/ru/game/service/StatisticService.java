package ru.game.service;

import ru.game.entity.Game;

import java.util.List;
import java.util.Map;

public interface StatisticService {
    List<Game> getUserGames(int userId);
    Map<String, Double> getRecord();
}

package ru.game.repository;

import ru.game.entity.Game;

import java.util.List;
import java.util.Map;

public interface StatisticDao {
    List<Game> getListGames(int userId);
    Map<String, Double> getRecords();
}

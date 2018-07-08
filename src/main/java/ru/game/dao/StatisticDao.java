package ru.game.dao;

import ru.game.entity.game.Game;

import java.util.List;
import java.util.Map;

public interface StatisticDao {
    Map<String, Double> getRecords();

    List<Game> getListGames(int userId);

}

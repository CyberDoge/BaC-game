package ru.game.service;

import ru.game.entity.Game;
import ru.game.repository.StatisticDao;
import ru.game.repository.StatisticDaoImpl;

import java.util.List;
import java.util.Map;

public class StatisticServiceImpl implements StatisticService {
    private StatisticDao statisticDao;

    public StatisticServiceImpl() {
        statisticDao = new StatisticDaoImpl();
    }

    @Override
    public List<Game> getUserGames(int userId) {
        return statisticDao.getListGames(userId);
    }

    @Override
    public Map<String, Double> getRecord() {
        return statisticDao.getRecords();
    }
}

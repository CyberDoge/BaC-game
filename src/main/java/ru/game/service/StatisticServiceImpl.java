package ru.game.service;

import ru.game.dao.StatisticDao;
import ru.game.entity.game.Game;
import ru.game.dao.StatisticDaoImp;

import java.util.List;
import java.util.Map;

public class StatisticServiceImpl implements StatisticService {
    private StatisticDao statisticDao;

    public StatisticServiceImpl(StatisticDao statisticDao) {
        this.statisticDao = statisticDao;
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

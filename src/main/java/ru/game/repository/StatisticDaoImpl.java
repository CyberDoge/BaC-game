package ru.game.repository;

import ru.game.entity.Game;
import ru.game.util.DbUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class StatisticDaoImpl implements StatisticDao {
    private Connection connection;

    public StatisticDaoImpl() {
        connection = DbUtil.getConnection();
    }

    @Override
    public List<Game> getListGames(int userId) {
        var list = new ArrayList<Game>();
        try {
            var statement = connection.prepareStatement("SELECT game.game_id, game.secret_num, attempt, datetime " +
                    "FROM game, attempts where game.game_id  = attempts.game_id and game.game_id IN " +
                    "(SELECT game_id FROM game WHERE user_id = ? AND leaved = 0) order by game.game_id ;");
            statement.setInt(1, userId);
            var result = statement.executeQuery();
            var last = 0;
            while (result.next()) {
                if (last < result.getInt(1)) {
                    list.add(new Game(list.size(), result.getInt(2)));
                    last = result.getInt(1);
                }
                list.get( list.size()-1).insertAttempt(result.getInt(3),result.getDate(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Map<String, Double> getRecords() {
        var comparator = new Comparator<>() {
            @Override
            public int compare(Object o1, Object o2) {
                return 1;
            }

            @Override
            public boolean equals(Object obj) {
                return false;
            }
        };
        var result = new TreeMap<String, Double>(comparator);
        try {
            var statement = connection.prepareStatement("select\n" +
                    "  u.username,\n" +
                    "  avg( a.maxnum) as avg\n" +
                    "from\n" +
                    "  game g\n" +
                    "  inner join\n" +
                    "  (select\n" +
                    "     max(num) as maxnum,\n" +
                    "     game_id\n" +
                    "   from attempts\n" +
                    "   group by game_id) as a on g.game_id = a.game_id\n" +
                    "  inner join user u on g.user_id = u.user_id\n" +
                    "where leaved = 0\n" +
                    "group by username\n" +
                    "order by avg\n");
            var resultSet = statement.executeQuery();
            while (resultSet.next())
                result.put(resultSet.getString(1), resultSet.getDouble(2));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

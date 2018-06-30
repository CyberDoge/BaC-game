package ru.game.dao;

import ru.game.entity.Game;
import ru.game.util.DbUtil;

import java.sql.SQLException;
import java.util.*;

public class StatisticDao {

    private static final String USER_GAMES_QUERY = "SELECT game.game_id, game.secret_num, attempt, datetime " +
            "FROM game, attempts where game.game_id  = attempts.game_id and game.game_id IN " +
            "(SELECT game_id FROM game WHERE user_id = ? AND leaved = 0) order by game.game_id";

    private static final String RECORDS_QUERY = "select u.username, avg(a.maxnum) as avg" +
            "  from game g inner join" +
            "  (select max(num) as maxnum, game_id" +
            "  from attempts" +
            "  group by game_id) as a on g.game_id = a.game_id" +
            "  inner join user u on g.user_id = u.user_id" +
            "  where leaved = 0  group by username order by avg";

    public List<Game> getListGames(int userId) {
        var list = new ArrayList<Game>();
        try (var statement = DbUtil.getConnection().prepareStatement(USER_GAMES_QUERY)) {
            statement.setInt(1, userId);
            var result = statement.executeQuery();
            var last = 0;
            while (result.next()) {
                if (last < result.getInt(1)) {
                    list.add(new Game(list.size(), result.getInt(2)));
                    last = result.getInt(1);
                }
                list.get(list.size() - 1).insertAttempt(result.getInt(3), result.getDate(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Map<String, Double> getRecords() {
        var comparator = new Comparator<>() {
            public int compare(Object o1, Object o2) {
                return 1;
            }
            public boolean equals(Object obj) {
                return false;
            }
        };
        var result = new TreeMap<String, Double>(comparator);
        try (var statement = DbUtil.getConnection().prepareStatement(RECORDS_QUERY)) {
            var resultSet = statement.executeQuery();
            while (resultSet.next())
                result.put(resultSet.getString(1), resultSet.getDouble(2));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

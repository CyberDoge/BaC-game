package ru.game.listener;

import ru.game.dao.GameDao;
import ru.game.dao.UserDao;
import ru.game.util.DbUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("userDao", new UserDao());
        sce.getServletContext().setAttribute("gameDao", new GameDao());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            DbUtil.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

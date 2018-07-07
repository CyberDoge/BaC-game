package ru.game.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.game.dao.GameDao;
import ru.game.dao.StatisticDao;
import ru.game.dao.UserDao;
import ru.game.util.DbUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.sql.SQLException;

@WebListener
public class AppContextListener implements ServletContextListener {

    private static final Logger LOGGER = LogManager.getLogger(AppContextListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("userDao", new UserDao());
        sce.getServletContext().setAttribute("gameDao", new GameDao());
        sce.getServletContext().setAttribute("statisticDao", new StatisticDao());
        try {
            DbUtil.init("/db.properties");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver is not found: ".concat(e.getMessage()));
        } catch (IOException e) {
            System.err.println("Error open property file: ".concat(e.getMessage()));
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            DbUtil.close();
        } catch (SQLException e) {
            LOGGER.error("Closing pool SQL-exception: ", e);
        }
    }
}

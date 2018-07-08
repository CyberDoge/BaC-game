package ru.game.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.game.dao.GameDaoImpl;
import ru.game.dao.StatisticDaoImp;
import ru.game.dao.UserDaoImpl;
import ru.game.util.DbUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

@WebListener
public class AppContextListener implements ServletContextListener {

    private static final Logger LOGGER = LogManager.getLogger(AppContextListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("userDao", new UserDaoImpl());
        sce.getServletContext().setAttribute("gameDao", new GameDaoImpl());
        sce.getServletContext().setAttribute("statisticDao", new StatisticDaoImp());
        try {
            var url = this.getClass().getClassLoader().getResource("/db.properties").toURI();
            var file = new File(url);
            DbUtil.init(file);
        } catch (IOException | ClassNotFoundException | URISyntaxException e) {
            e.printStackTrace();
            System.exit(-1);
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

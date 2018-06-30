package ru.game.listener;

import ru.game.repository.UserDao;
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
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            DbUtil.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

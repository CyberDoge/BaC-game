package ru.game.listener;

import ru.game.repository.UserDaoImpl;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("userDao", new UserDaoImpl());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}

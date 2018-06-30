package ru.game.servlet;

import com.google.gson.Gson;
import ru.game.dao.UserDao;
import ru.game.service.StatisticService;
import ru.game.service.StatisticServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/home")
public class HomeServlet extends HttpServlet {
    private StatisticService statisticService;

    @Override
    public void init() throws ServletException {
        statisticService= new StatisticServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("username", req.getSession(false).getAttribute("username"));
        getServletContext().getRequestDispatcher("/WEB-INF/view/user/home.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var session = req.getSession(false);
        var user = ((UserDao) getServletContext().getAttribute("userDao")).findUserByUsername((String) session.getAttribute("username"));
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        var json = new Gson().toJson(statisticService.getUserGames(user.getUserId()));
        resp.getWriter().write(json);
    }
}

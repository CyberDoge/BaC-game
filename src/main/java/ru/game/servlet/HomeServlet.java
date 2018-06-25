package ru.game.servlet;

import com.google.gson.Gson;
import ru.game.entity.User;
import ru.game.service.StatisticService;
import ru.game.service.StatisticServiceImpl;
import ru.game.service.UserService;
import ru.game.service.UserServiceImpl;

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
        req.setAttribute("username", ((User)(req.getSession(false).getAttribute("user"))).getUsername());
        getServletContext().getRequestDispatcher("/WEB-INF/view/user/home.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var session = req.getSession(false);
        var user = ((User) session.getAttribute("user"));
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        var json = new Gson().toJson(statisticService.getUserGames(user.getUserId()));
        resp.getWriter().write(json);
    }
}

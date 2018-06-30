package ru.game.servlet;

import ru.game.service.StatisticService;
import ru.game.service.StatisticServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/records")
public class RecordServlet extends HttpServlet {
    private StatisticService service;

    @Override
    public void init() throws ServletException {
        service = new StatisticServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            req.setAttribute("auth", true);
        }
        req.setAttribute("map", service.getRecord());
        req.getRequestDispatcher("/WEB-INF/view/records.jsp").forward(req, resp);
    }
}

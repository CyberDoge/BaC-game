package ru.game.servlet;

import com.google.gson.Gson;
import ru.game.dao.GameDao;
import ru.game.dao.UserDao;
import ru.game.service.GameService;
import ru.game.service.GameServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/game")
public class GameServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/view/user/game.jsp").forward(req, resp);
        var session = req.getSession(false);
        var user = ((UserDao) getServletContext().getAttribute("userDao"))
                .findUserByUsername((String) session.getAttribute("username"));
        if (session.getAttribute("gameService") == null) {
            var gameService = new GameServiceImpl();
            session.setAttribute("gameService", gameService);
            gameService.createGame(user.getUserId(), ((GameDao) getServletContext().getAttribute("gameDao")));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var gson = new Gson();
        var session = req.getSession(false);
        var text = gson.fromJson(req.getReader(), String.class);
        var json = "";
        var gameService = (GameService) (session.getAttribute("gameService"));
        try {
            var response = gameService.createResponse(text, ((GameDao) getServletContext().getAttribute("gameDao")));
            json = gson.toJson(response);
            if (response.isEnd()) session.setAttribute("gameService", null);
        } catch (NumberFormatException e) {
            json = gson.toJson(e);
        }
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }

}

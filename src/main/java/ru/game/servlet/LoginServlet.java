package ru.game.servlet;

import ru.game.repository.UserDao;
import ru.game.repository.UserDaoImpl;
import ru.game.validator.AuthValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("logout") != null) {
            request.getSession().invalidate();
            response.sendRedirect("/");
            return;
        }
        request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //todo check request return null
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        var messages = AuthValidator.validateLogin(username, password, (UserDao) getServletContext().getAttribute("userDao"));
        if (messages.isEmpty()) {
            var session = request.getSession();
            session.setAttribute("username", username);
            session.setMaxInactiveInterval(60 * 60);
            response.sendRedirect(request.getContextPath() + "user/home");
            return;
        }
        request.setAttribute("messages", messages);
        request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
    }
}

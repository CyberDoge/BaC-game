package ru.game.servlet;

import ru.game.entity.User;
import ru.game.service.UserService;
import ru.game.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserServiceImpl();
    }

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
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        var messages = new ArrayList<String>();
        if (username == null || username.isEmpty()) {
            messages.add("Please enter username");
        }

        if (password == null || password.isEmpty()) {
            messages.add("Please enter password");
        }

        if (messages.isEmpty()) {
            User user = userService.loginUser(username, password);
            if (user != null) {
                var session = request.getSession();
                session.setAttribute("user", user);
                session.setMaxInactiveInterval(60 * 60);
                response.sendRedirect(request.getContextPath() + "user/home");
                return;
            } else {
                messages.add("Invalid login or password. Please try again.");
            }
        }

        request.setAttribute("messages", messages);
        request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
    }
}

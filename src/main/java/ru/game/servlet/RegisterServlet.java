package ru.game.servlet;

import ru.game.dao.UserDao;
import ru.game.service.UserService;
import ru.game.service.UserServiceImpl;
import ru.game.validator.AuthValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/sign_up"})
public class RegisterServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() {
        this.userService = (UserService) getServletContext().getAttribute("userService");
        if (userService == null) {
            userService = new UserServiceImpl((UserDao) getServletContext().getAttribute("userDao"));
            getServletContext().setAttribute("userService", userService);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/view/sign_up.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirm = req.getParameter("confirm_password");
        String rememberMe = req.getParameter("remember");
        if (username == null || password == null || confirm == null) {
            req.setAttribute("messages", "You try to send null");
            req.getRequestDispatcher("/WEB-INF/view/sign_up.jsp").forward(req, resp);
            return;
        }
        var messages = AuthValidator.validateRegistration(username, password, confirm, (UserDao) getServletContext().getAttribute("userDao"));
        if (messages.isEmpty()) {
            userService.sendCookies(rememberMe, resp, username);
            userService.registerUser(username, password);
            req.getSession().setAttribute("username", username);
            resp.sendRedirect(req.getContextPath() + "user/home");
            return;
        }
        req.setAttribute("messages", messages);
        req.getRequestDispatcher("/WEB-INF/view/sign_up.jsp").forward(req, resp);
    }
}

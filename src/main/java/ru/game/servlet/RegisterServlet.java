package ru.game.servlet;

import ru.game.dao.UserDao;
import ru.game.validator.AuthValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/sign_up"})
public class RegisterServlet extends HttpServlet {
       @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/view/sign_up.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirm = req.getParameter("confirm_password");
        var messages = AuthValidator.validateRegistration(username, password, confirm, (UserDao) getServletContext().getAttribute("userDao"));
        if (messages.isEmpty()) {
            AuthValidator.registerUser(username, password, (UserDao) getServletContext().getAttribute("userDao"));
            req.getSession().setAttribute("username", username);
            resp.sendRedirect(req.getContextPath() + "user/home");
            return;
        }
        req.setAttribute("messages", messages);
        req.getRequestDispatcher("/WEB-INF/view/sign_up.jsp").forward(req, resp);
    }
}

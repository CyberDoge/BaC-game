package ru.game.servlet;

import ru.game.service.UserService;
import ru.game.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = {"/sign_up"})
public class RegServlet extends HttpServlet {
    private UserService service;

    @Override
    public void init() throws ServletException {
        service = new UserServiceImpl();
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
        List<String> messages = new ArrayList();
        if (!Pattern.matches("^[a-zA-Z0-9]*$", username)) messages.add("Username can contain only lat. letter and digits");
        if(username.length() > 40) messages.add("too long username");
        if(password.length() > 40) messages.add("too long password");
        if (username.isEmpty()) messages.add("Enter username");
        if (password.isEmpty() || confirm.isEmpty()) messages.add("Enter and confirm password");
        if (!confirm.equals(password)) messages.add("Passwords are not confirm");
        if (service.containsUser(username)) messages.add("User with this name has already registered");
        if (messages.isEmpty()) {
            var user = service.createUser(username, password);
            req.getSession().setAttribute("user", user);
            resp.sendRedirect(req.getContextPath() + "user/home");
            return;
        }
        req.setAttribute("messages", messages);
        req.getRequestDispatcher("/WEB-INF/view/sign_up.jsp").forward(req, resp);
    }
}

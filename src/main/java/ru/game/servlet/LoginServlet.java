package ru.game.servlet;

import ru.game.dao.UserDao;
import ru.game.dao.UserDaoImpl;
import ru.game.service.UserService;
import ru.game.service.UserServiceImpl;
import ru.game.validator.AuthValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("logout") != null) {
            var cookies = request.getCookies();
            for (var c : cookies) {
                c.setMaxAge(0);
            }
            userService.invalidCookies((String) request.getSession(false).getAttribute("username"));
            request.getSession().invalidate();
            response.sendRedirect("/");
            return;
        }

        var cookies = request.getCookies();
        var usernameCookie = userService.authByCookies(cookies);
        if (usernameCookie != null) {
            auth(request, response, usernameCookie);
            return;
        }
        request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("remember");


        //with username and password
        if (username == null || password == null) {
            request.setAttribute("messages", "You try to send null");
            request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
            return;
        }
        var messages = AuthValidator.validateLogin(username, password, (UserDaoImpl) getServletContext().getAttribute("userDao"));
        if (messages.isEmpty()) {
            userService.sendCookies(rememberMe, response, username);
            auth(request, response, username);
            return;
        }
        request.setAttribute("messages", messages);
        request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
    }

    //login
    private void auth(HttpServletRequest request, HttpServletResponse response, String username) throws IOException {
        var session = request.getSession();
        session.setAttribute("username", username);
        session.setMaxInactiveInterval(60 * 60);
        response.sendRedirect(request.getContextPath() + "user/home");
    }
}

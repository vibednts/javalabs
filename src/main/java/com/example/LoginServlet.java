package com.example;

import com.example.dao.UserDao;
import com.example.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/templates/login.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User user = userDao.authenticate(email, password);

        if (user != null) {
            // Зберігаємо юзера в сесію
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            // Перенаправляємо на список завдань
            resp.sendRedirect(req.getContextPath() + "/tasks");
        } else {
            req.setAttribute("error", "Невірний email або пароль!");
            req.getRequestDispatcher("/WEB-INF/templates/login.ftl").forward(req, resp);
        }
    }
}
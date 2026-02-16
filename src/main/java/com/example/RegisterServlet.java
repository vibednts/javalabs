package com.example;

import com.example.dao.UserDao;
import com.example.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/templates/register.ftl").forward(req, resp);

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        User newUser = new User(name, email, password);
        userDao.saveUser(newUser);

        // Після реєстрації відправляємо на сторінку логіну
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
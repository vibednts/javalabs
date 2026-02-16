package com.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate(); // Знищуємо сесію
        }

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");


        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
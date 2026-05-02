package com.example;

import com.example.model.User;
import com.example.service.FirebaseUserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// Цей сервлет обробляє одразу кілька маршрутів
@WebServlet({"/user/login", "/user/register", "/auth/session", "/auth/logout"})
public class UserServlet extends HttpServlet {

    private FirebaseUserService firebaseUserService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        this.firebaseUserService = new FirebaseUserService();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

        // Відображення сторінок (GET запити)
        if ("/user/login".equals(path)) {
            req.getRequestDispatcher("/WEB-INF/templates/login.ftl").forward(req, resp);
        } else if ("/user/register".equals(path)) {
            req.getRequestDispatcher("/WEB-INF/templates/register.ftl").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

        // 1. Обробка токена від Firebase (POST /auth/session)
        if ("/auth/session".equals(path)) {
            handleAuthSession(req, resp);
        }
        // 2. Вихід з системи (POST або GET /auth/logout)
        else if ("/auth/logout".equals(path)) {
            handleLogout(req, resp);
        }
    }

    // Метод, який приймає idToken від JavaScript і створює сесію
    private void handleAuthSession(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        Map<String, String> responseData = new HashMap<>();

        try {
            // Читаємо JSON з тіла запиту
            BufferedReader reader = req.getReader();
            JsonNode jsonNode = objectMapper.readTree(reader);
            String idToken = jsonNode.get("idToken").asText();

            // Перевіряємо токен і синхронізуємо юзера з БД
            User user = firebaseUserService.authenticateAndSync(idToken);

            // Створюємо HTTP-сесію
            HttpSession session = req.getSession(true);
            session.setAttribute("user", user);

            responseData.put("status", "success");
            responseData.put("redirectUrl", req.getContextPath() + "/tasks"); // Куди йти після логіну

            resp.getWriter().write(objectMapper.writeValueAsString(responseData));

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            responseData.put("status", "error");
            responseData.put("message", "Authentication failed: " + e.getMessage());
            resp.getWriter().write(objectMapper.writeValueAsString(responseData));
        }
    }

    private void handleLogout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate(); // Знищуємо сесію бекенда
        }
        resp.sendRedirect(req.getContextPath() + "/user/login");
    }
}
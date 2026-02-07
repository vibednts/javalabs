package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends HttpServlet {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pathInfo = req.getPathInfo();
        String userId = null;

        if (pathInfo != null && pathInfo.length() > 1) {
            userId = pathInfo.substring(1);
        }

        String type = req.getParameter("type");

        HttpSession session = req.getSession();
        String sessionUser = (String) session.getAttribute("username");

        String lastVisit = "New User";
        if (req.getCookies() != null) {
            for (Cookie c : req.getCookies()) {
                if ("lastVisit".equals(c.getName())) {
                    lastVisit = c.getValue();
                }
            }
        }


        String format = req.getParameter("format");
        if ("json".equals(format)) {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("userId_pathVariable", userId);
            jsonResponse.put("type_requestParam", type);
            jsonResponse.put("sessionUser", sessionUser);
            jsonResponse.put("cookieLastVisit", lastVisit);

            mapper.writeValue(resp.getWriter(), jsonResponse);
            return;
        }

        req.setAttribute("userId", userId != null ? userId : "Не вказано (спробуйте /user/55)");
        req.setAttribute("type", type != null ? type : "Не вказано");
        req.setAttribute("sessionUser", sessionUser != null ? sessionUser : "Гість");
        req.setAttribute("cookieInfo", lastVisit);

        req.getRequestDispatcher("/WEB-INF/templates/user.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String action = req.getParameter("action");

        if (username != null && !username.isEmpty()) {
            req.getSession().setAttribute("username", username);
        }

        Cookie cookie = new Cookie("lastVisit", String.valueOf(System.currentTimeMillis()));
        cookie.setMaxAge(60 * 60 * 24); // 1 день
        resp.addCookie(cookie);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        Map<String, String> result = new HashMap<>();
        result.put("status", "success");
        result.put("message", "Дані збережено в Сесію та Куки!");
        result.put("receivedUser", username);

        mapper.writeValue(resp.getWriter(), result);
    }
}
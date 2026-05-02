package com.example.filter;

import com.example.model.User;
import com.example.util.FirebaseConfig;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Map;

@WebFilter("/*")
public class ViewModelFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // 1. Додаємо базовий шлях (contextPath), щоб зручно підключати стилі/скрипти у шаблонах
        request.setAttribute("contextPath", httpRequest.getContextPath());

        // 2. Прокидаємо налаштування Firebase для фронтенду
        Map<String, Object> firebaseConfig = FirebaseConfig.getFirebaseWebConfig();
        for (Map.Entry<String, Object> entry : firebaseConfig.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }

        // 3. Перевіряємо сесію і прокидаємо дані користувача, якщо він авторизований
        HttpSession session = httpRequest.getSession(false);
        boolean isAuthenticated = false;

        if (session != null && session.getAttribute("user") != null) {
            isAuthenticated = true;
            User user = (User) session.getAttribute("user");

            request.setAttribute("currentUserId", user.getId());
            request.setAttribute("currentUserEmail", user.getEmail());
            request.setAttribute("currentUserName", user.getName());
            request.setAttribute("currentFirebaseUid", user.getFirebaseUid());
        }
        request.setAttribute("isAuthenticated", isAuthenticated);

        // Передаємо управління далі
        chain.doFilter(request, response);
    }
}
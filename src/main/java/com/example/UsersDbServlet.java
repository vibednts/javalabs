package com.example;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import javax.naming.*;
import javax.sql.DataSource;

import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/db/users")
public class UsersDbServlet extends HttpServlet {

    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        try {
            Context ctx = new InitialContext();
            dataSource =
                    (DataSource) ctx.lookup("java:comp/env/jdbc/MyDB");
        } catch (NamingException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
            throws ServletException, IOException {

        List<Map<String,String>> users = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM users")) {

            while (rs.next()) {
                Map<String,String> u = new HashMap<>();
                u.put("id", rs.getString("id"));
                u.put("name", rs.getString("name"));
                u.put("email", rs.getString("email"));
                users.add(u);
            }

        } catch (SQLException e) {
            throw new ServletException(e);
        }

        req.setAttribute("users", users);
        req.getRequestDispatcher("/WEB-INF/templates/db-users.ftl")
                .forward(req, resp);
    }
}

package com.example;

import com.example.dao.TaskDao;
import com.example.model.Task;
import com.example.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/tasks")
public class TaskServlet extends HttpServlet {
    private TaskDao taskDao = new TaskDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        // Перевірка, чи залогінений юзер. Тепер кидаємо на /user/login
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/user/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        String action = req.getParameter("action");

        if (action != null) {
            int taskId = Integer.parseInt(req.getParameter("id"));
            if (action.equals("delete")) {
                taskDao.deleteTask(taskId, user.getId());
            } else if (action.equals("complete")) {
                taskDao.updateTaskStatus(taskId, user.getId(), Task.Status.DONE);
            }
            resp.sendRedirect(req.getContextPath() + "/tasks");
            return;
        }

        List<Task> tasks = taskDao.getTasksByUserId(user.getId());
        req.setAttribute("tasks", tasks);
        // Ім'я ми вже передаємо через фільтр (currentUserName), але можна і так залишити
        req.setAttribute("userName", user.getName());
        req.getRequestDispatcher("/WEB-INF/templates/tasks.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/user/login");
            return;
        }

        req.setCharacterEncoding("UTF-8");
        User user = (User) session.getAttribute("user");
        String title = req.getParameter("title");
        String description = req.getParameter("description");

        // Зверни увагу: тепер ми передаємо об'єкт user, а не просто user.getId()
        Task task = new Task(title, description, Task.Status.NEW, user);
        taskDao.saveTask(task);

        resp.sendRedirect(req.getContextPath() + "/tasks");
    }
}
package com.example.dao;

import com.example.model.Task;
import com.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;

import java.util.List;

public class TaskDao {

    // ==========================================
    // 1. CREATE
    // ==========================================
    public void saveTask(Task task) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(task);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // ==========================================
    // 2. READ
    // ==========================================
    public List<Task> getTasksByUserId(int userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Використовуємо HQL для роботи з об'єктами
            String sql = "FROM Task t WHERE t.user.id = :userId " +
                    "ORDER BY " +
                    "CASE t.status WHEN 'NEW' THEN 1 WHEN 'IN_PROGRESS' THEN 2 WHEN 'DONE' THEN 3 ELSE 4 END ASC, " +
                    "CASE t.priority WHEN 'HIGH' THEN 1 WHEN 'MEDIUM' THEN 2 WHEN 'LOW' THEN 3 ELSE 4 END ASC, " +
                    "t.deadline ASC";

            Query<Task> query = session.createQuery(sql, Task.class);
            query.setParameter("userId", userId);
            return query.list();
        }
    }

    // ==========================================
    // 3. UPDATE (Зміна статусу)
    // ==========================================
    public void updateTaskStatus(int taskId, int userId, Task.Status newStatus) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // HQL UPDATE
            MutationQuery query = session.createMutationQuery("UPDATE Task t SET t.status = :status WHERE t.id = :id AND t.user.id = :userId");
            query.setParameter("status", newStatus);
            query.setParameter("id", taskId);
            query.setParameter("userId", userId);

            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // ==========================================
    // 4. DELETE
    // ==========================================
    public void deleteTask(int taskId, int userId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // HQL DELETE
            MutationQuery query = session.createMutationQuery("DELETE FROM Task t WHERE t.id = :id AND t.user.id = :userId");
            query.setParameter("id", taskId);
            query.setParameter("userId", userId);

            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
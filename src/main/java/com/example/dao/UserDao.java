package com.example.dao;

import com.example.model.User;
import com.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class UserDao {

    // ==========================================
    // 1. CREATE (Збереження нового користувача)
    // ==========================================
    public void saveUser(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // ==========================================
    // 2. READ (Пошук користувача)
    // ==========================================

    // Пошук за Firebase UID (Головний метод для нашої нової аутентифікації)
    public User getUserByFirebaseUid(String firebaseUid) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery("FROM User WHERE firebaseUid = :uid", User.class);
            query.setParameter("uid", firebaseUid);
            return query.uniqueResult();
        }
    }

    // Пошук за нашим внутрішнім ID
    public User getUserById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(User.class, id);
        }
    }

    // ==========================================
    // 3. UPDATE (Оновлення даних)
    // ==========================================
    public void updateUser(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(user); // merge оновлює існуючий запис
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
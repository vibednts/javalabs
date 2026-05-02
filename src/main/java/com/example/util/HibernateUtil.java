package com.example.util;

import com.example.model.Task;
import com.example.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.InputStream;
import java.util.Properties;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Properties properties = new Properties();
            try (InputStream input = HibernateUtil.class.getClassLoader().getResourceAsStream("project.properties")) {
                if (input == null) {
                    throw new RuntimeException("Не знайдено project.properties у resources");
                }
                properties.load(input);
            }

            Configuration configuration = new Configuration();

            configuration.setProperty("hibernate.connection.driver_class", properties.getProperty("db.driver"));
            configuration.setProperty("hibernate.connection.url", properties.getProperty("db.url"));
            configuration.setProperty("hibernate.connection.username", properties.getProperty("db.user"));
            configuration.setProperty("hibernate.connection.password", properties.getProperty("db.password"));

            configuration.setProperty("hibernate.dialect", properties.getProperty("hibernate.dialect"));
            configuration.setProperty("hibernate.show_sql", properties.getProperty("hibernate.show_sql"));
            configuration.setProperty("hibernate.hbm2ddl.auto", properties.getProperty("hibernate.hbm2ddl.auto"));

            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(Task.class);

            return configuration.buildSessionFactory();

        } catch (Throwable ex) {
            System.err.println("Помилка ініціалізації SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
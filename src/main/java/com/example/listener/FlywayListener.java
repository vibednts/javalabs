package com.example.listener;

import org.flywaydb.core.Flyway;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.io.InputStream;
import java.util.Properties;

@WebListener
public class FlywayListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Ініціалізація Flyway: перевірка бази даних...");

        try {
            // Читаємо налаштування з project.properties
            Properties properties = new Properties();
            try (InputStream input = getClass().getClassLoader().getResourceAsStream("project.properties")) {
                if (input == null) {
                    throw new RuntimeException("Не знайдено project.properties");
                }
                properties.load(input);
            }

            String url = properties.getProperty("db.url");
            String user = properties.getProperty("db.user");
            String password = properties.getProperty("db.password");

            // Налаштовуємо та запускаємо Flyway
            Flyway flyway = Flyway.configure()
                    .dataSource(url, user, password)
                    .baselineOnMigrate(true) // Дозволяє працювати з уже існуючою базою
                    .load();

            // Накатуємо міграції!
            flyway.migrate();
            System.out.println("Flyway: Міграції успішно застосовані!");

        } catch (Exception e) {
            System.err.println("Помилка під час виконання міграцій Flyway!");
            e.printStackTrace();
            throw new RuntimeException("Не вдалося запустити міграції", e);
        }
        com.example.util.HibernateUtil.getSessionFactory();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Виконується при зупинці Tomcat, тут нічого робити не треба
    }
}
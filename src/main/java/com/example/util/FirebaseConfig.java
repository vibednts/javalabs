package com.example.util;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class FirebaseConfig {

    private static final Properties properties = new Properties();
    private static boolean isInitialized = false;

    // Ініціалізація Firebase Admin SDK для бекенду
    public static void init() {
        if (isInitialized) return;

        try {
            // 1. Читаємо project.properties з ресурсів
            InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("project.properties");
            if (input == null) {
                throw new RuntimeException("Не знайдено project.properties");
            }
            properties.load(input);

            String serviceAccountFilename = properties.getProperty("firebase.service.account.path");

            // 2. Читаємо JSON-ключ ТАКОЖ з ресурсів (замість FileInputStream)
            InputStream serviceAccount = Thread.currentThread().getContextClassLoader().getResourceAsStream(serviceAccountFilename);

            if (serviceAccount == null) {
                throw new RuntimeException("Не знайдено файл ключа Firebase у папці resources: " + serviceAccountFilename);
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
            isInitialized = true;
            System.out.println("Firebase Admin SDK успішно ініціалізовано.");

        } catch (Exception e) {
            System.err.println("Помилка ініціалізації Firebase: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Отримання конфігурації для передачі на фронтенд (FreeMarker views)
    public static Map<String, Object> getFirebaseWebConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put("apiKey", properties.getProperty("firebase.web.apiKey"));
        config.put("authDomain", properties.getProperty("firebase.web.authDomain"));
        config.put("projectId", properties.getProperty("firebase.web.projectId"));
        config.put("storageBucket", properties.getProperty("firebase.web.storageBucket"));
        config.put("messagingSenderId", properties.getProperty("firebase.web.messagingSenderId"));
        config.put("appId", properties.getProperty("firebase.web.appId"));
        return config;
    }
}
package com.example.service;

import com.example.dao.UserDao;
import com.example.model.User;
import com.example.util.FirebaseConfig;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

public class FirebaseUserService {

    private final UserDao userDao;

    public FirebaseUserService() {
        this.userDao = new UserDao();
        // Обов'язково впевнюємося, що Firebase ініціалізовано перед роботою
        FirebaseConfig.init();
    }

    public User authenticateAndSync(String idToken) throws Exception {
        // 1. Firebase Admin SDK перевіряє криптографічний підпис токена
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);

        // 2. Витягуємо дані з розшифрованого токена
        String uid = decodedToken.getUid();
        String email = decodedToken.getEmail();
        String name = decodedToken.getName();

        // Якщо користувач реєструвався просто по email, Firebase може не мати його імені.
        // Задамо дефолтне (наприклад, частину до @)
        if (name == null || name.isEmpty()) {
            name = email.split("@")[0];
        }

        // 3. Шукаємо користувача в нашій MySQL базі за firebase_uid
        User user = userDao.getUserByFirebaseUid(uid);

        // 4. Якщо користувача ще немає в БД - створюємо нового
        if (user == null) {
            user = new User(uid, email, name);
            userDao.saveUser(user);
        } else {
            // Опціонально: якщо дані змінилися на стороні Firebase (наприклад, ім'я),
            // оновлюємо їх у нашій базі.
            boolean isUpdated = false;

            if (!email.equals(user.getEmail())) {
                user.setEmail(email);
                isUpdated = true;
            }
            if (!name.equals(user.getName())) {
                user.setName(name);
                isUpdated = true;
            }

            if (isUpdated) {
                userDao.updateUser(user);
            }
        }

        // Повертаємо локального користувача (з його локальним ID, яким ми прив'язуємо таски)
        return user;
    }
}
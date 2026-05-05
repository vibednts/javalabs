# 📝 Java ToDoList

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![Firebase](https://img.shields.io/badge/Firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=black)
![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![Bootstrap](https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white)

Веб-додаток для управління завданнями.

## ✨ Основний функціонал

* MVC Архітектура: Серверна частина побудована на класичних Java Servlets (Jakarta EE) для обробки HTTP-запитів, а динамічний рендеринг HTML-сторінок реалізовано через шаблонізатор Freemarker.
* **🔐 Авторизація:** Надійна автентифікація через Google або Email/Пароль за допомогою Firebase Auth.
* **🗂 Категоризація:** Розподіл завдань за категоріями (🎓 Навчання, 💼 Робота, 🏠 Особисте).
* **🔥 Пріоритети:** Візуальні мітки важливості (🔴 Високий, 🟡 Середній, 🟢 Низький).
* **⏳ Дедлайни:** Встановлення точного часу виконання з автоматичним розрахунком часу, що залишився.
* **⚠️ Розумна індикація:** Прострочені завдання автоматично підсвічуються червоним кольором.
* **🔄 База даних:** Автоматичні міграції за допомогою Flyway та ORM Hibernate для роботи з MySQL.

## 🚀 Запуск проєкту локально

### 1. Вимоги
* Java 17+
* Maven
* MySQL Server (локально на порту 3306)

### 2. Збірка та запуск
Відкрийте термінал (або командний рядок) у кореневій папці проєкту (там, де знаходиться файл `pom.xml`) та виконайте наступні команди:

Крок 1. Очищення та збірка проєкту (компіляція Java-коду):
mvn clean package

Крок 2. Запуск вбудованого сервера Tomcat:
mvn cargo:run

### 3. Використання
Коли в терміналі з'явиться повідомлення про успішний запуск Tomcat, відкрийте браузер і перейдіть за адресою:

👉 http://localhost:8080/
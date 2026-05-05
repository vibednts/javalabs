package com.example.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.sql.Timestamp;

@Entity
@Table(name = "tasks")
public class Task {

    public enum Status {
        NEW, IN_PROGRESS, DONE
    }

    public enum Category {
        STUDY, WORK, PERSONAL
    }

    public enum Priority {
        LOW, MEDIUM, HIGH
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Status status = Status.NEW;

    // Зв'язок з таблицею users
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", length = 20)
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", length = 20)
    private Priority priority;

    @Column(name = "deadline")
    private Timestamp deadline;


    // Порожній конструктор обов'язковий для Hibernate
    public Task() {}

    public Task(String title, String description, Status status, User user, Category category, Priority priority, Timestamp deadline) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.user = user;
        this.category = category;
        this.priority = priority;
        this.deadline = deadline;
    }

    // Getters / Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public Timestamp getDeadline() { return deadline; }
    public void setDeadline(Timestamp deadline) { this.deadline = deadline; }

    public boolean isOverdue() {
        return deadline != null && deadline.getTime() < System.currentTimeMillis() && status != Status.DONE;
    }

    public String getTimeLeftStr() {
        if (deadline == null || status == Status.DONE) return "";
        long millis = deadline.getTime() - System.currentTimeMillis();

        if (millis < 0) return "Прострочено!";

        long days = millis / (1000 * 60 * 60 * 24);
        if (days > 0) return "Залишилось: " + days + " дн.";

        long hours = millis / (1000 * 60 * 60);
        return "Залишилось: " + hours + " год.";
    }



}
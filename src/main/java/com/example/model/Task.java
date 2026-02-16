package com.example.model;
import java.sql.Timestamp;

public class Task {

    //status enum
    public enum Status {
        NEW, IN_PROGRESS, DONE
    }

    private int id;
    private String title;
    private String description;
    private Status status; // NEW, IN_PROGRESS, DONE
    private int userId;
    private Timestamp createdAt;

    //constructors
    public Task() {}

    public Task(String title, String description, Status status, int userId) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.userId = userId;
    }

    //Get/Set-methods
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}

package com.example.itmproject.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categorized")
public class Categorized {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private long userId;
    private long categoryId;

    public Categorized(long userId, long categoryId) {
        this.userId = userId;
        this.categoryId = categoryId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
}

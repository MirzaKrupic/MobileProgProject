package com.example.itmproject.Entities;

import androidx.room.Entity;

@Entity(primaryKeys = {"userId", "categoryId"})
public class UserCategoryCrossref {
    public long userId;
    public long categoryId;

    public UserCategoryCrossref(long userId, long categoryId) {
        this.userId = userId;
        this.categoryId = categoryId;
    }
}
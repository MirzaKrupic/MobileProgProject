package com.example.itmproject.Entities;

import androidx.room.Entity;

@Entity(primaryKeys = {"categoryId", "id"})
public class CategoryCategorizedCrossref {
    public long userId;
    public long id;
}

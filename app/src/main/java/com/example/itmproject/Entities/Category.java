package com.example.itmproject.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "category")
public class Category {
    @PrimaryKey(autoGenerate = true)
    private long categoryId;
    private String name = "Majstor";

    public Category(String name) {
        this.name = name;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Category[] populateCategories(){
        return new Category[]{
                new Category("Kitchen"),
                new Category("Bathroom"),
                new Category("Electricity"),
                new Category("Home appliances fix"),
                new Category("Outdoor"),
                new Category("Deep clean"),
                new Category("Water")
        };
    }
}

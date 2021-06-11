package com.example.itmproject.Entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "category")
public class Category {
    @PrimaryKey(autoGenerate = true)
    private long catId;
    private String name = "Majstor";

    public Category(String name) {
        this.name = name;
    }

    public long getCatId() {
        return catId;
    }

    public void setCatId(long catId) {
        this.catId = catId;
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
    public static List<Category> populateCategoriesToList(){
        ArrayList<Category> categories = new ArrayList<Category>();
        categories.add(new Category("Kitchen"));
        categories.add(new Category("Bathroom"));
        categories.add(new Category("Electricity"));
        categories.add(new Category("Home appliances fix"));
        categories.add(new Category("Outdoor"));
        categories.add(new Category("Deep clean"));
        categories.add(new Category("Water"));
        return categories;
    }
}

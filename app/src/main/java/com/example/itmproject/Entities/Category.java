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
    private long id;
    private String name;

    public Category(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Category[] populateCategories(){
        return new Category[]{
                new Category(0,"Kitchen"),
                new Category(1,"Bathroom"),
                new Category(2,"Electricity"),
                new Category(3,"Home appliances fix"),
                new Category(4,"Outdoor"),
                new Category(5,"Deep clean"),
                new Category(6,"Water")
        };
    }
    public static List<Category> populateCategoriesToList(){
        ArrayList<Category> categories = new ArrayList<Category>();
        categories.add(new Category(0,"Kitchen"));
        categories.add(new Category(1,"Bathroom"));
        categories.add(new Category(2,"Electricity"));
        categories.add(new Category(3,"Home appliances fix"));
        categories.add(new Category(4,"Outdoor"));
        categories.add(new Category(5,"Deep clean"));
        categories.add(new Category(6,"Water"));
        return categories;
    }
}

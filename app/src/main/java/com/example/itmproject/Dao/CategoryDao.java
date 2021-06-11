package com.example.itmproject.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.itmproject.Entities.Category;
import com.example.itmproject.Entities.UserWithCategories;

import java.util.List;

@Dao
public interface CategoryDao {

    @Query("SELECT * FROM category")
    List<Category> getAll();

    @Query("SELECT * FROM category WHERE name = :name LIMIT 1")
    Category getByName(String name);

    @Insert
    void insertAll(Category... dataEntities);
}

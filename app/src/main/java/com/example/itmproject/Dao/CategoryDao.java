package com.example.itmproject.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.itmproject.Entities.Category;
import com.example.itmproject.Entities.UserWithCategories;

import java.util.List;

@Dao
public interface CategoryDao {
    @Insert
    void insertAll(Category... categories);

    @Query("SELECT * FROM users")
    List<UserWithCategories> getAllUsersWithCategories();

    @Query("SELECT * FROM category")
    List<Category> getAll();
}

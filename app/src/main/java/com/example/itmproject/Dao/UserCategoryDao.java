package com.example.itmproject.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.itmproject.Entities.Category;
import com.example.itmproject.Entities.CategoryAndUser;
import com.example.itmproject.Entities.ReviewAndUser;
import com.example.itmproject.Entities.UserCategoryCrossref;
import com.example.itmproject.Entities.UserWithCategories;

import java.util.List;

@Dao
public interface UserCategoryDao {

    @Transaction
    @Query("SELECT * FROM users")
    public List<UserWithCategories> getUsersWithCategory();

    @Transaction
    @Query("SELECT * FROM users WHERE userId = :id")
    public List<UserWithCategories> getUsersWithCategoryById(Long id);

    @Transaction
    @Query("SELECT * FROM category")
    public List<CategoryAndUser> getCategoryWithUser();

    @Insert
    void add(UserCategoryCrossref userCategoryCrossref);
}

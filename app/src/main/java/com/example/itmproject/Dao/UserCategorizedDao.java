package com.example.itmproject.Dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.itmproject.CategoryAndCategorization;
import com.example.itmproject.Entities.CategoriyAndCategorized;
import com.example.itmproject.Entities.Category;
import com.example.itmproject.Entities.ReviewAndUser;
import com.example.itmproject.Entities.UserAndCategorized;

import java.util.List;

@Dao
public interface UserCategorizedDao {


    @Transaction
    @Query("SELECT * FROM category WHERE categoryId = :categoryId")
    public List<CategoriyAndCategorized> categoryByUser(Long categoryId);

    @Transaction
    @Query("SELECT * FROM category WHERE categoryId = :categoryId LIMIT 1")
    public CategoriyAndCategorized oneCategoryByUser(Long categoryId);

}

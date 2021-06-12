package com.example.itmproject.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.itmproject.Entities.Categorized;
import com.example.itmproject.Entities.Review;

import java.util.List;

@Dao
public interface CategorizedDao {

    @Insert
    void addCategorized(Categorized categorized);

    @Query("SELECT * FROM categorized WHERE userId = :userId")
    List<Categorized> getCategorizedByUserId(Long userId);

    @Query("SELECT * FROM categorized WHERE categoryId = :categoryId")
    List<Categorized> getCategorizedByCategoryId(Long categoryId);
}

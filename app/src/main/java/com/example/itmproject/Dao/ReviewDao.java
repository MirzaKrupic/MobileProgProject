package com.example.itmproject.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.itmproject.Entities.Review;

@Dao
public interface ReviewDao {
    @Insert
    void addReview(Review review);
}

package com.example.itmproject.Dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.itmproject.Entities.Category;
import com.example.itmproject.Entities.Review;
import com.example.itmproject.Entities.ReviewAndUser;
import com.example.itmproject.Entities.UserAndReview;

import java.util.List;

@Dao
public interface UserReviewDao {

    @Transaction
    @Query("SELECT * FROM reviews WHERE userId  = :userId")
    public List<ReviewAndUser> getUserReviews(Long userId);
}

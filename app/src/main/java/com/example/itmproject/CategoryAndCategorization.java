package com.example.itmproject;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.itmproject.Entities.Review;
import com.example.itmproject.Entities.User;

import java.util.List;

public class CategoryAndCategorization {

    @Embedded public User user;
    @Relation(
            parentColumn = "id",
            entityColumn = "userId"
    )
    public List<Review> review;
}

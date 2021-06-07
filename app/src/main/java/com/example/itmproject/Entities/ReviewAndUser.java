package com.example.itmproject.Entities;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class ReviewAndUser {
    @Embedded
    public Review review;
    @Relation(
            parentColumn = "userId",
            entityColumn = "id"
    )
    public List<User> user;
}

package com.example.itmproject.Entities;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.itmproject.Entities.User;

import java.util.List;

public class UserWithCategories {
    @Embedded public User user;
    @Relation(
            parentColumn = "userId",
            entityColumn = "categoryId",
            associateBy = @Junction(
                    UserCategoryCrossref.class
            )
    )
    public List<Category> categories;
}

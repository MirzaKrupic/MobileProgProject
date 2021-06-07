package com.example.itmproject.Entities;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.itmproject.Entities.User;

import java.util.List;

public class UserWithCategories {
    @Embedded public User user;
    @Relation(
            parentColumn = "id",
            entityColumn = "userid",
            associateBy = @Junction(
                    value = UserCategoryCrossref.class,
                    parentColumn = "userId",
                    entityColumn = "categoryId"
            )
    )
    public List<Category> categories;
}

package com.example.itmproject.Entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserAndCategorized {
    @Embedded
    public Categorized categorized;
    @Relation(
            parentColumn = "id",
            entityColumn = "userId"
    )
    public List<User> user;
}

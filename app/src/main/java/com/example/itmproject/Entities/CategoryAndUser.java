package com.example.itmproject.Entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CategoryAndUser {
    @Embedded
    public Category category;
    @Relation(
            parentColumn = "id",
            entityColumn = "catId"
    )
    public List<User> user;
}

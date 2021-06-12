package com.example.itmproject.Entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CategoriyAndCategorized {
    @Embedded
    public Category category;
    @Relation(
            parentColumn = "categoryId",
            entityColumn = "id"
    )
    public List<Categorized> categorized;
}

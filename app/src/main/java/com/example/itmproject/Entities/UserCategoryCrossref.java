package com.example.itmproject.Entities;

import androidx.room.Entity;

@Entity(primaryKeys = {"id", "catId"})
public class UserCategoryCrossref {
    public long id;
    public long catId;
}
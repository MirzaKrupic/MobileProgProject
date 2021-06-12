package com.example.itmproject.Entities;

import androidx.room.Entity;

@Entity(primaryKeys = {"userId", "id"})
public class UserCategorizedCrossref {
    public long userId;
    public long id;
}

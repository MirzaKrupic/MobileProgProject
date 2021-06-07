package com.example.itmproject.Entities;

import androidx.room.Entity;

@Entity(primaryKeys = {"id", "reviewid"})
public class UserReviewCrossref {
    public long id;
    public long reviewid;
}

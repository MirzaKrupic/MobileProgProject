package com.example.itmproject.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reviews")
public class Review {

    @PrimaryKey(autoGenerate = true)
    private long reviewid;

    private String comment;
    private int grade;
    private long userId;

    public long getReviewid() {
        return reviewid;
    }

    public void setReviewid(long reviewid) {
        this.reviewid = reviewid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Review(String comment, int grade, long userId) {
        this.comment = comment;
        this.grade = grade;
        this.userId = userId;
    }
}

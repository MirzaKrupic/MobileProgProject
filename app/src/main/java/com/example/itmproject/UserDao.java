package com.example.itmproject;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {

    @Insert
    void add(User user);

    @Query("SELECT id FROM users WHERE username = :username and password = :password LIMIT 1")
    Long loginUser(String username, String password);
}

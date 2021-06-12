package com.example.itmproject.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.itmproject.Entities.User;
import com.example.itmproject.Entities.UserWithCategories;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void add(User user);

    @Query("SELECT userId FROM users WHERE username = :username and password = :password LIMIT 1")
    Long loginUser(String username, String password);

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    User getUserByUsername(String username);

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    User getUserByEmail(String email);

    @Query("SELECT * FROM users WHERE userId = :id")
    User getUserById(Long id);

    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    @Query("UPDATE users SET name = :name, phone = :phone, description = :description, email = :email, location = :location WHERE userId = :id")
    void updateUser(String name, String phone, String description, String email, String location, Long id);

    @Update
    void signIn(User user);
}

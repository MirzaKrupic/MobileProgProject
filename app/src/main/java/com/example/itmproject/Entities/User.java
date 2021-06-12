package com.example.itmproject.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {

    @PrimaryKey(autoGenerate = true)
    private long userId;

    private String email;
    private String username;
    private String password;
    private String name;
    private String phone;
    private String location;
    private String description;
    private Long catId;

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.name = null;
        this.phone = null;
        this.location = null;
        this.description = null;
    }

    public Long getCatId() {
        return catId;
    }

    public void setCatId(Long catId) {
        this.catId = catId;
    }

    /*public User(String email, String username, String password, String name, String phone, String location) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.location = location;
        this.description = null;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

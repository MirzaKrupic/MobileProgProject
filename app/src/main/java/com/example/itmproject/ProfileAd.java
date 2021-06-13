package com.example.itmproject;

public class ProfileAd {
    private int _imageResourceId;
    private String _title;
    private String _description;

    public ProfileAd(int imageResourceId, String title, String description) {
        _imageResourceId = imageResourceId;
        _title = title;
        _description = description;

    }

    public int getImageResourceId() {
        return _imageResourceId;
    }

    public String getTitle() {
        return _title;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String description) {
        _description = description;
    }
}
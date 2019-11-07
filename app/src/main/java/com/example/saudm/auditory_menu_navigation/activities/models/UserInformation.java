package com.example.saudm.auditory_menu_navigation.activities.models;

import com.google.firebase.database.Exclude;

public class UserInformation {

    private String userId;
    private String username;

    public UserInformation(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

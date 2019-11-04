package com.example.myapplication.model;

import java.io.Serializable;

public  class Admin extends Student implements Serializable {
    public Admin(String firstName, String lastName, String userName, String level, String profilePicUrl){
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.profilePicUrl = profilePicUrl;
        this.level = level;
        this.profilePicUrl =profilePicUrl;
    }

    public void accepterInvitation(){

    }
}

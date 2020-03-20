package com.example.christian_augustyn_aandroid_developer_halfwaythere;

public class ProfileInfo {
    private String user, email, fname, lname;

    public ProfileInfo(String user, String email, String fname, String lname) {
        this.user = user;
        this.email = email;
        this.fname = fname;
        this.lname = lname;
    }

    public String getUser(){
        return this.user;
    }

    public String getFirstName() {
        return this.fname;
    }

    public String getLastName() {
        return this.lname;
    }

    public String getEmail() {
        return this.email;
    }
}

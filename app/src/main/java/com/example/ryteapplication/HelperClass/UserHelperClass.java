package com.example.ryteapplication.HelperClass;

public class UserHelperClass {

    String fullname;
    String username;
    String email;
    String password;
    String userid;

    public UserHelperClass(String email, String fullname, String password, String userid, String username) {
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.userid = userid;
    }

    public UserHelperClass() {
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

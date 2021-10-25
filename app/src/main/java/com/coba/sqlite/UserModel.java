package com.coba.sqlite;

public class UserModel {
    public String id, username, email, password;

    public UserModel(String id,String username,String email,String password){
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }
}

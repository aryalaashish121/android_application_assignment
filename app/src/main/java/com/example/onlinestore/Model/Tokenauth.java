package com.example.onlinestore.Model;

public class Tokenauth {

    private String token;

    private UserDetails users;

    public Tokenauth(String token, UserDetails users) {
        this.token = token;
        this.users = users;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDetails getUsers() {
        return users;
    }

    public void setUsers(UserDetails users) {
        this.users = users;
    }
}

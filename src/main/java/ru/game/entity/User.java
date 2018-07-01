package ru.game.entity;

public class User {
    private int userId;
    private String username;
    private String password;
    private String token;

    public User(int userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        token = null;
    }

    public User(int userId, String username, String password, String token) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.token = token;
    }

    public String getToken() {
        if (token == null) return "";
        return token;
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

    public int getUserId() {
        return userId;
    }


    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}

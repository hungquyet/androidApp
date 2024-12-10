package com.example.callapitourdulich.response;

public class LoginResponse {
    private String message;
    private String token;

    // Getters và setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

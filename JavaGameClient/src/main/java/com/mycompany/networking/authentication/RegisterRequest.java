package com.mycompany.networking.authentication;

public class RegisterRequest implements AuthMessage {
    private String username;
    private String password;
   
    public RegisterRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
   
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

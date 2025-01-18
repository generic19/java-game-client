/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.networking.authentication;

public class RegisterRequest implements AuthMessage {
    private String username;
    private String password;
    private String email;

   
    public RegisterRequest(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

   
    public String getUsername() {
        return username;
    }

  

    public String getPassword() {
        return password;
    }

   
    

    

   
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.networking.authentication;

/**
 *
 * @author basel
 */
public class SignInWithTokenRequest implements AuthMessage {
    private final String token;

    public SignInWithTokenRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.networking.authentication;

/**
 *
 * @author AhmedAli
 */
public class SignInResponse implements AuthMessage{
    
    private boolean success;
    private String token;
    private String errorMessage;

    public SignInResponse(boolean success, String token, String errorMessage) {
        this.success = success;
        this.token = token;
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getToken() {
        return token;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return "SignInResponse{" + "success=" + success + ", token=" + token + ", errorMessage=" + errorMessage + '}';
    }
}

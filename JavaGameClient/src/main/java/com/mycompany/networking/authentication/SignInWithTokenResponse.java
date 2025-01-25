/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.networking.authentication;

/**
 *
 * @author basel
 */
public class SignInWithTokenResponse implements AuthMessage {
    private final boolean success;

    public SignInWithTokenResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public String toString() {
        return "SignInWithTokenResponse{" + "success=" + success + '}';
    }
}

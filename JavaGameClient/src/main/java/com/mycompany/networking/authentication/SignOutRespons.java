/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.networking.authentication;

/**
 *
 * @author ArwaKhaled
 */
public class SignOutRespons implements AuthMessage {
    private boolean success;

    public SignOutRespons(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.networking.authentication;

/**
 *
 * @author ArwaKhaled
 */
public class SignOutRequest implements AuthMessage {
    private String userName;

    public SignOutRequest(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}

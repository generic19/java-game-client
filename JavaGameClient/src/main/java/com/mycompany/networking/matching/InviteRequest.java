/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.networking.matching;

/**
 *
 * @author AhmedAli
 */
public class InviteRequest implements MatchingMessage{
    private final String userName;

    public InviteRequest(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return "InviteRequest{" + "userName=" + userName + '}';
    }
}

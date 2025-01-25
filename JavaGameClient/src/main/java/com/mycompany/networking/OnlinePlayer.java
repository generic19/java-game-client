/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.networking;

import java.io.Serializable;

/**
 *
 * @author basel
 */
public class OnlinePlayer implements Serializable {
    private final String username;
    private final int score;

    public OnlinePlayer(String username, int score) {
        this.username = username;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "OnlinePlayer{" + "username=" + username + ", score=" + score + '}';
    }
}

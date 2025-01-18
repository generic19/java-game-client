/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.networking.game;

import com.mycompany.game.Player;

/**
 *
 * @author ArwaKhaled
 */
public class GameStartMessage implements GameMessage {
    private String OpponentUserName;
    private int opponentScore ;
    private Player player;

    public GameStartMessage(String OpponentUserName, int opponentScore, Player player) {
        this.OpponentUserName = OpponentUserName;
        this.opponentScore = opponentScore;
        this.player = player;
    }

    public String getOpponentUserName() {
        return OpponentUserName;
    }

    public int getOpponentScore() {
        return opponentScore;
    }

    public Player getPlayer() {
        return player;
    }
    
}

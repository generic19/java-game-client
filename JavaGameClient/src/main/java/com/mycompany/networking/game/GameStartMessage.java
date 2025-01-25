/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.networking.game;

import com.mycompany.game.Player;
import com.mycompany.networking.OnlinePlayer;

/**
 *
 * @author ArwaKhaled
 */
public class GameStartMessage implements GameMessage {
    private final OnlinePlayer player;
    private final OnlinePlayer opponent;
    private final Player playerTurn;

    public GameStartMessage(OnlinePlayer player, OnlinePlayer opponent, Player playerTurn) {
        this.player = player;
        this.opponent = opponent;
        this.playerTurn = playerTurn;
    }

    public OnlinePlayer getPlayer() {
        return player;
    }

    public OnlinePlayer getOpponent() {
        return opponent;
    }

    public Player getPlayerTurn() {
        return playerTurn;
    }

    @Override
    public String toString() {
        return "GameStartMessage{" + "player=" + player + ", opponent=" + opponent + ", playerTurn=" + playerTurn + '}';
    }
}

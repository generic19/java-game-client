/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.networking.game;

import com.mycompany.game.GameMove;
/**
 *
 * @author AhmedAli
 */
public class GameMoveMessage<M extends GameMove> implements GameMessage {
    private final M move;

    public GameMoveMessage(M move) {
        this.move = move;
    }

    public M getMove() {
        return move;
    } 

    @Override
    public String toString() {
        return "GameMoveMessage{" + "move=" + move + '}';
    }
}

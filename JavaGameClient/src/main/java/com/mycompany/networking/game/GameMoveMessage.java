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
public class GameMoveMessage implements GameMessage{
    
    private GameMove move;

    public GameMoveMessage(GameMove move) {
        this.move = move;
    }

    public GameMove getMove() {
        return move;
    }
    
    
}

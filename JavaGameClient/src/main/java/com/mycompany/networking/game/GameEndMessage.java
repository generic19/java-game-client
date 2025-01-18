/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.networking.game;

import com.mycompany.networking.Message;


public class GameEndMessage implements GameMessage {

    private boolean isWinner;
    private boolean isLoser;
    private int score;

 
    public GameEndMessage(boolean isWinner, boolean isLoser, int score) {
        this.isWinner = isWinner;
        this.isLoser = isLoser;
        this.score = score;
    }

   
    public boolean getWinner() {
        return isWinner;
    }

   

    public boolean getLoser() {
        return isLoser;
    }

   

    public int getScore() {
        return score;
    }

   

}


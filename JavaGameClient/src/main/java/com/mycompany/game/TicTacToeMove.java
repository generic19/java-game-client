/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.game;

/**
 *
 * @author ayasa
 */
public class TicTacToeMove implements GameMove {
     final int index;
     Player player;
    public TicTacToeMove(int index,Player player) {
        if(index<0||index>8){
            throw new UnsupportedOperationException("position must be between 0 and 8");
        }
        this.index=index;
        this.player=player;
    }
     @Override
    public int getPosition(){
        return index;
    }
    
    
    

    

    @Override
    public boolean equals(GameMove other) {
        
        if(other==null ||getClass()!=other.getClass()){
        return false;}
        TicTacToeMove otherMove=(TicTacToeMove)other;
        return this.index==otherMove.getPosition()&&this.player.equals(otherMove.getPlayer());
        
        
    }

    @Override
    public Player getPlayer() {
        return player;
    }
    
}

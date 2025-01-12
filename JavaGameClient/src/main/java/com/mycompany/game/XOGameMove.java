/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.game;

/**
 *
 * @author ArwaKhaled
 */
public class XOGameMove implements GameMove{

    
    private int index ;

    public int getRow() {
        return index/3;
    }

    public int getCol() {
        return  index%3;
    }

    public int getIndex() {
        return index;
    }

    public XOGameMove(int index) {
        this.index = index;
    }
    
    public boolean equals(GameMove other) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Player getPlayer() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}

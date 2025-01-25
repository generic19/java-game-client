/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.game;

import java.io.Serializable;

/**
 *
 * @author ArwaKhaled
 */
public class XOGameMove implements GameMove, Serializable {

    private final Player player;
    private final int index;

    public int getRow() {
        return index / 3;
    }

    public int getCol() {
        return index % 3;
    }

    public int getIndex() {
        return index;
    }

    public XOGameMove(int index, Player player) {
        if (index < 0 || index > 8) {
            throw new UnsupportedOperationException("position must be between 0 and 8");
        }
        this.index = index;
        this.player = player;
    }

    @Override
    public boolean equals(GameMove other) {
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        XOGameMove otherMove = (XOGameMove) other;

        return this.index == otherMove.index && this.player.equals(otherMove.player);
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public String toString() {
        return "XOGameMove{" + "player=" + player + ", index=" + index + '}';
    }
}

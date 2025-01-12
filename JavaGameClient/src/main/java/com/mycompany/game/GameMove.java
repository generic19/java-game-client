package com.iti.javafx.group1.game;

public interface GameMove {
    
    /**
     * Checks if `other` is the same move and the exact type.
     *
     * @param other rhs of the equality check.
     * @return `true` if equal, `false` otherwise.
     */
    boolean equals(GameMove other);
    
    /**
     * @return the player of the move.
     */
    Player getPlayer();
}

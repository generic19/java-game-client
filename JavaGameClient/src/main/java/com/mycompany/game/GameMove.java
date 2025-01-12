package com.mycompany.game;

public interface GameMove {
    
    /**
     * Checks if `other` is the same move and the exact type.
     *
     * @param other  of the equality check.
     * @return `true` if equal, `false` otherwise.
     */
    int getPosition();
    boolean equals(GameMove other);

    /**
     * @return the player of the move.
     */
    Player getPlayer();
}

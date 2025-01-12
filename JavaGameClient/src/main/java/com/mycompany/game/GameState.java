package com.mycompany.game;

import java.util.Iterator;


public interface GameState<M extends GameMove> {


    /**
     * Gets whose turn it is to play next, or null if this is an end state.
     *
     * @return Player of next turn, or `null` if this is an end state.
     */
    Player getNextTurnPlayer();
    
    /**
     * Checks if `move` is a valid move at this state.
     *
     * @param move the move.
     * @return `true` if `move` is valid, `false` otherwise.
     */
    boolean isValidMove(M move);
    
    /**
     * Gets all valid moves at state. Intended to be used with game AI.
     *
     * @return iterator of valid moves.
     */
    Iterator<M> getValidMoves();
    
    /**
     * Returns a new state with `move` played.
     *
     * @param move the move.
     * @return new state with `move` played.
     * @throws IllegalStateException if `move` is not a valid move at this state.
     */
    GameState<M> play(M move) throws IllegalStateException;
    
    /**
     * Checks if this is an end state, meaning a state with no valid moves left. End states can be either winning states
     * for one of the players, or a draw state.
     *
     * @return `true` if this is an end state, `false` otherwise.
     */
    boolean isEndState();
    
    /**
     * Gets the winner of this end state, or `null` if it is a draw.
     *
     * @return winning player, or `null` if it is a draw.
     * @throws IllegalStateException if this is not an end state.
     */
    Player getWinner() throws IllegalStateException;
    
    /**
     * @return the last move, or null if no moves have been played.
     */
    M getLastMove();
}

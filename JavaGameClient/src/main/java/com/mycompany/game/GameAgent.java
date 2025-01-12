package com.iti.javafx.group1.game;

/**
 * Intended to use with Game interface to implement an AI.
 * @param <M> implementation of the GameMove interface.
 * @param <S> implementation of the GameState interface.
 */
public interface GameAgent<M extends GameMove, S extends GameState<M>> {
    
    /**
     * Takes the current game state, and returns the best move to play.
     *
     * @param state current game state.
     * @return next move.
     */
    M getNextMove(S state);
}

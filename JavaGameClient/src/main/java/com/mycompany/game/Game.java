package com.mycompany.game;

public interface Game<M extends GameMove, S extends GameState<M>> {
    
    /**
     * @return the current game state.
     */
    S getState();
    
    /**
     * Plays the given move from the current game state, and changes the state to the state where the move has been
     * played.
     *
     * @param move the move.
     * @throws IllegalStateException if the given move is not a valid move for the current state, or the move is
     *     from a different player than the player whose turn it is.
     */
    void play(M move) throws IllegalStateException;
    
    /**
     * Attaches a game agent (AI) to a player to play automatically on its turn.
     *
     * @param player the player to attach the agent to.
     * @param agent a game agent.
     */
    void attachAgent(Player player, GameAgent<M, S> agent);
    
    /**
     * Removes the agent attached to the given player.
     *
     * @param player the player to remove the agent from.
     */
    void detachAgent(Player player);
    
    /**
     * Adds a listener to be notified when the state of the game changes.
     *
     * @param listener the listener.
     */
    void addListener(Listener<M, S> listener);
    
    /**
     * Removes the listener.
     *
     * @param listener the listener.
     */
    void removeListener(Listener<M, S> listener);
    
    /**
     * Intended to use for changing the UI when the state changes.
     *
     * @param <M> implementation of the GameMove interface.
     * @param <S> implementation of the GameState interface.
     */
    interface Listener<M extends GameMove, S extends GameState<M>> {
        
        /**
         * Receives new state when the game state changes.
         *
         * @param newState newly changed state.
         */
        void onStateChange(S newState);
    }
}

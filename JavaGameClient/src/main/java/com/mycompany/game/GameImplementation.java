/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.game;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author AhmedAli
 */
public class GameImplementation implements Game<GameMove, GameState<GameMove>>{
    
    private GameState<GameMove> currentState;
    private GameAgent[] gameAgents = new GameAgent[2];
    private final List <Listener<GameMove, GameState<GameMove>>> listeners = new ArrayList<>();
    

    public GameImplementation(GameState<GameMove> currentState) {
        this.currentState = currentState;
    }
    
    
    @Override
    public GameState<GameMove> getState() {
        return currentState;
    }

    @Override
    public void play(GameMove move) throws IllegalStateException {
        if(currentState.isValidMove(move)){
            currentState = currentState.play(move);
            for(Listener listener: listeners){
                listener.onStateChange(currentState);
            }
            Player nextTurnPlayer = currentState.getNextTurnPlayer();
            GameAgent agent = gameAgents[nextTurnPlayer.ordinal()];
            if(agent != null){
                play(agent.getNextMove(currentState));
            }
        }else{
            throw new IllegalStateException("Invalid move: " + move);
        }
    }

    @Override
    public void attachAgent(Player player, GameAgent<GameMove, GameState<GameMove>> agent) {
        
        gameAgents[player.ordinal()] = agent; 
        
    }

    @Override
    public void detachAgent(Player player) {
        gameAgents[player.ordinal()] = null; 
    }

    @Override
    public void addListener(Listener<GameMove, GameState<GameMove>> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(Listener<GameMove, GameState<GameMove>> listener) {
        listeners.remove(listener);
    }
    
}

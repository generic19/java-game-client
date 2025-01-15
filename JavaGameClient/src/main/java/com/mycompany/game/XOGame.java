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
public class XOGame implements Game<XOGameMove, XOGameState>{
    
    private XOGameState currentState;
    private GameAgent[] gameAgents = new GameAgent[2];
    private final List <Listener<XOGameMove, XOGameState>> listeners = new ArrayList<>();
    
    
    public XOGame() {
        this.currentState = new XOGameState();
    }
    
    
    @Override
    public XOGameState getState() {
        return currentState;
    }

    @Override
    public synchronized void play(XOGameMove move) throws IllegalStateException {
        if(currentState.isValidMove(move)){
            currentState = currentState.play(move);
            for(Listener listener: listeners){
                listener.onStateChange(currentState);
            }
            Player nextTurnPlayer = currentState.getNextTurnPlayer();
            GameAgent agent = gameAgents[nextTurnPlayer.ordinal()];
            if(agent != null && !currentState.isEndState()){
                play((XOGameMove) agent.getNextMove(currentState));
            }
        }else{
            throw new IllegalStateException("Invalid move: " + move);
        }
    }

    @Override
    public void attachAgent(Player player, GameAgent agent) {
        gameAgents[player.ordinal()] = agent; 
    }

    @Override
    public void detachAgent(Player player) {
        gameAgents[player.ordinal()] = null; 
    }

    @Override
    public void addListener(Listener<XOGameMove, XOGameState> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(Listener<XOGameMove, XOGameState> listener) {
        listeners.remove(listener);
    }
    
}

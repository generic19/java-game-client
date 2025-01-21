/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.networking.game;

import com.mycompany.networking.OnlinePlayer;
import com.mycompany.game.*;
import com.mycompany.networking.Communicator;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author basel
 */
public class GameManagerImpl<M extends GameMove, S extends GameState<M>> implements GameManager<M, S> {
    private final Communicator communicator;
    private final Set<Listener<S>> listeners = new HashSet<>();

    private final Communicator.Listener<GameStartMessage> gameStartListener;
    private final Communicator.Listener<GameEndMessage> gameEndListener;
    private final Communicator.Listener<GameStateMessage<S>> gameStateListener;
    
    private boolean isListening = false;
    
    public GameManagerImpl(Communicator communicator) {
        this.communicator = communicator;
        
        gameStartListener = (GameStartMessage message, boolean hasError) -> {
            if (hasError) {
                onError();
                return;
            }
            
            OnlinePlayer opponent = new OnlinePlayer(message.getOpponentUserName(), message.getOpponentScore());
            
            listeners.forEach(l -> {
                l.onGameStart(message.getPlayer(), opponent);
            });
        };
        
        gameEndListener = (GameEndMessage message, boolean hasError) -> {
            if (hasError) {
                onError();
                return;
            }
            
            listeners.forEach(l -> {
                l.onGameEnd(message.isWinner(), message.isLoser(), message.getScore());
            });
        };
        
        gameStateListener = (GameStateMessage<S> message, boolean hasError) -> {
            if (hasError) {
                onError();
                return;
            }
            
            listeners.forEach(l -> {
                l.onGameState(message.getState());
            });
        };
    }
    
    private void startListening() {
        communicator.setListener(GameStartMessage.class, gameStartListener);
        communicator.setListener(GameEndMessage.class, gameEndListener);
        communicator.setListener(GameStateMessage.class, gameStateListener);
        isListening = true;
    }
    
    private void stopListening() {
        communicator.unsetListener(GameStartMessage.class, gameStartListener);
        communicator.unsetListener(GameEndMessage.class, gameEndListener);
        communicator.unsetListener(GameStateMessage.class, gameStateListener);
        isListening = false;
    }
    
    private void onError() {
        stopListening();
        
        listeners.forEach(l -> l.onError("Could not connect to the game."));
        listeners.clear();
    }
    
    @Override
    public void addListener(Listener<S> listener) {
        listeners.add(listener);
        
        if (!isListening) {
            startListening();
        }
    }

    @Override
    public void removeListener(Listener<S> listener) {
        listeners.remove(listener);
        
        if (listeners.isEmpty() && isListening) {
            stopListening();
        }
    }

    @Override
    public void move(M gameMove) {
        communicator.sendMessage(new GameMoveMessage<>(gameMove));
    }

    @Override
    public void leave() {
        communicator.sendMessage(new GameLeaveMessage());
    }
}

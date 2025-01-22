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
public class XOGameManager implements GameManager<XOGameMove, XOGameState> {
    private static XOGameManager instance;
    
    private Listener<XOGameState> listener;
    
    private final Communicator.Listener<GameStartMessage> gameStartListener;
    private final Communicator.Listener<GameEndMessage> gameEndListener;
    private final Communicator.Listener<XOGameStateMessage> gameStateListener;
    
    private boolean isListening = false;
    
    public static XOGameManager getInstance() {
        if (instance == null) {
            synchronized (XOGameManager.class) {
                if (instance == null) {
                    instance = new XOGameManager();
                }
            }
        }
        return instance;
    }
    
    private XOGameManager() {
        gameStartListener = (GameStartMessage message) -> {
            OnlinePlayer opponent = new OnlinePlayer(message.getOpponentUserName(), message.getOpponentScore());
            listener.onGameStart(message.getPlayer(), opponent);
        };
        
        gameEndListener = (GameEndMessage message) -> {
            listener.onGameEnd(message.isWinner(), message.isLoser(), message.getScore());
        };
        
        gameStateListener = (XOGameStateMessage message) -> {
            listener.onGameState(message.getState());
        };
    }
    
    private void startListening() {
        Communicator comm = Communicator.getInstance();
        
        comm.setMessageListener(GameStartMessage.class, gameStartListener);
        comm.setMessageListener(GameEndMessage.class, gameEndListener);
        comm.setMessageListener(XOGameStateMessage.class, gameStateListener);
        
        isListening = true;
    }
    
    private void stopListening() {
        Communicator comm = Communicator.getInstance();
        
        comm.unsetMessageListener(GameStartMessage.class);
        comm.unsetMessageListener(GameEndMessage.class);
        comm.unsetMessageListener(GameStateMessage.class);
        
        isListening = false;
    }
    
    private void onConnectionError() {
        stopListening();
        
        listener.onError("Could not connect to the game.");
        unsetListener();
    }
    
    @Override
    public void setListener(Listener<XOGameState> listener) {
        this.listener = listener;
        
        if (!isListening) {
            startListening();
        }
    }
    
    @Override
    public void unsetListener() {
        listener = null;
        
        if (isListening) {
            stopListening();
        }
    }
    
    @Override
    public void move(XOGameMove gameMove) {
        Communicator.getInstance().sendMessage(new GameMoveMessage<>(gameMove));
    }
    
    @Override
    public void leave() {
        Communicator.getInstance().sendMessage(new GameLeaveMessage());
    }
}

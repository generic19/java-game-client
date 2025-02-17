/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.networking.game;

import com.mycompany.game.*;
import com.mycompany.networking.Communicator;

/**
 *
 * @author basel
 */
public class XOGameManager implements GameManager<XOGameMove, XOGameState> {

    private static volatile XOGameManager instance;

    private Listener<XOGameState> listener;

    private final Communicator.Listener<GameStartMessage> gameStartListener;
    private final Communicator.Listener<GameEndMessage> gameEndListener;
    private final Communicator.Listener<XOGameStateMessage> gameStateListener;

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
            if (message != null) {
                listener.onGameStart(message.getPlayer(), message.getOpponent(), message.getPlayerTurn());
            } else {
                listener.onGameError("Lost connection to server.");
            }
        };

        gameEndListener = (GameEndMessage message) -> {
            if (message != null) {
                listener.onGameEnd(message.isWinner(), message.isLoser(), message.getScore());
            } else {
                listener.onGameError("Lost connection to server.");
            }
        };

        gameStateListener = (XOGameStateMessage message) -> {
            if (message != null) {
                listener.onGameState(message.getState());
            } else {
                listener.onGameError("Lost connection to server.");
            }
        };
    }

    private void startListening() {
        Communicator comm = Communicator.getInstance();

        comm.setMessageListener(GameStartMessage.class, gameStartListener);
        comm.setMessageListener(GameEndMessage.class, gameEndListener);
        comm.setMessageListener(XOGameStateMessage.class, gameStateListener);
    }

    private void stopListening() {
        Communicator comm = Communicator.getInstance();

        comm.unsetMessageListener(GameStartMessage.class);
        comm.unsetMessageListener(GameEndMessage.class);
        comm.unsetMessageListener(GameStateMessage.class);
    }

    @Override
    public void setListener(Listener<XOGameState> listener) {
        this.listener = listener;
        startListening();
    }

    @Override
    public void unsetListener() {
        listener = null;
        stopListening();
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

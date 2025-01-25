/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.networking.game;

import com.mycompany.networking.OnlinePlayer;
import com.mycompany.game.GameMove;
import com.mycompany.game.GameState;
import com.mycompany.game.Player;

/**
 *
 * @author basel
 */
public interface GameManager<M extends GameMove, S extends GameState<M>> {
    void setListener(Listener<S> listener);
    void unsetListener();
    void move(M gameMove);
    void leave();
    
    interface Listener<S extends GameState> {
        void onGameStart(OnlinePlayer player, OnlinePlayer opponent, Player playerTurn);
        void onGameState(S newState);
        void onGameEnd(boolean isWinner, boolean isLoser, int score);
        void onGameError(String errorMessage);
    }
}

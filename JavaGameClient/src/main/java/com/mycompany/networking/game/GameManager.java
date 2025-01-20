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
    void addListener(Listener<S> listener);
    void removeListener(Listener<S> listener);
    void move(M gameMove);
    void leave();
    
    interface Listener<S extends GameState> {
        void onGameStart(Player player, OnlinePlayer opponent);
        void onGameState(S newState);
        void onGameEnd(boolean isWinner, boolean isLoser, int score);
        void onError(String errorMessage);
    }
}

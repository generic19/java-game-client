/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.networking.game;

import com.mycompany.game.GameState;

/**
 *
 * @author basel
 */
public class GameStateMessage<S extends GameState> implements GameMessage {
    private final S state;

    public GameStateMessage(S state) {
        this.state = state;
    }

    public S getState() {
        return state;
    }

    @Override
    public String toString() {
        return "GameStateMessage{" + "state=" + state + '}';
    }
}

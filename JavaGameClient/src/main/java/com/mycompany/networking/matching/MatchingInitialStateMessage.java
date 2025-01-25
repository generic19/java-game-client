/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.networking.matching;

import com.mycompany.networking.OnlinePlayer;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author basel
 */
public class MatchingInitialStateMessage implements MatchingMessage {
    private final List<OnlinePlayer> available;
    private final List<OnlinePlayer> inGame;

    public MatchingInitialStateMessage(List<OnlinePlayer> available, List<OnlinePlayer> inGame) {
        this.available = available;
        this.inGame = inGame;
    }

    public List<OnlinePlayer> getAvailable() {
        return Collections.unmodifiableList(available);
    }

    public List<OnlinePlayer> getInGame() {
        return Collections.unmodifiableList(inGame);
    }    

    @Override
    public String toString() {
        return "MatchingInitialStateMessage{" + "available=" + available + ", inGame=" + inGame + '}';
    }
}

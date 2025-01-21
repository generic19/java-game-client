/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.networking.matching;

import com.mycompany.networking.OnlinePlayer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author basel
 */
public class MatchingInitialStateMessage implements MatchingMessage {
    private final ArrayList<OnlinePlayer> available;
    private final ArrayList<OnlinePlayer> inGame;

    public MatchingInitialStateMessage(ArrayList<OnlinePlayer> available, ArrayList<OnlinePlayer> inGame) {
        this.available = available;
        this.inGame = inGame;
    }

    public List<OnlinePlayer> getAvailable() {
        return Collections.unmodifiableList(available);
    }

    public List<OnlinePlayer> getInGame() {
        return Collections.unmodifiableList(inGame);
    }    
}

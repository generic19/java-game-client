/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.networking.matching;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author basel
 */
public class MatchingInitialStateMessage implements MatchingMessage {
    private final ArrayList<String> available;
    private final ArrayList<String> inGame;

    public MatchingInitialStateMessage(ArrayList<String> available, ArrayList<String> inGame) {
        this.available = available;
        this.inGame = inGame;
    }

    public List<String> getAvailable() {
        return Collections.unmodifiableList(available);
    }

    public List<String> getInGame() {
        return Collections.unmodifiableList(inGame);
    }    
}

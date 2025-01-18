/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.networking.matching;

import com.mycompany.networking.Message;

/**
 * A class representing a matching update message.
 * Implements the MatchingMessage interface.
 */
public class MatchingUpdateMessage implements MatchingMessage {

    private String username;
    private UpdateType updateType;
    private Target target;

    
    public enum UpdateType {
        ADD, REMOVE
    }

    
    public enum Target {
        AVAILABLE, IN_GAME
    }

   
    public MatchingUpdateMessage(String username, UpdateType updateType, Target target) {
        this.username = username;
        this.updateType = updateType;
        this.target = target;
    }

    

}

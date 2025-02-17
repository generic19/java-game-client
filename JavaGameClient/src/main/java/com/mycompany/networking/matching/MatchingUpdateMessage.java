/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.networking.matching;

import com.mycompany.networking.OnlinePlayer;

/**
 * A class representing a matching update message.
 * Implements the MatchingMessage interface.
 */
public class MatchingUpdateMessage implements MatchingMessage {

    private final OnlinePlayer player;
    private final UpdateType updateType;
    private final Target target;

    public MatchingUpdateMessage(OnlinePlayer player, UpdateType updateType, Target target) {
        this.player = player;
        this.updateType = updateType;
        this.target = target;
    }

    public OnlinePlayer getPlayer() {
        return player;
    }

    public UpdateType getUpdateType() {
        return updateType;
    }

    public Target getTarget() {
        return target;
    }
    
    public enum UpdateType {
        ADD, REMOVE
    }

    public enum Target {
        AVAILABLE, IN_GAME
    }

    @Override
    public String toString() {
        return "MatchingUpdateMessage{" + "player=" + player + ", updateType=" + updateType + ", target=" + target + '}';
    }
}

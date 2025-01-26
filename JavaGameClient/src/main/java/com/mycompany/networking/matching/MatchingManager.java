/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.networking.matching;

import com.mycompany.networking.Communicator;
import com.mycompany.networking.OnlinePlayer;
import java.util.*;

/**
 *
 * @author ArwaKhaled
 */
public class MatchingManager {

    private final Map<String, OnlinePlayer> availablePlayers = new LinkedHashMap<>();
    private final Map<String, OnlinePlayer> inGamePlayers = new LinkedHashMap<>();

    private final Communicator.Listener<MatchingInitialStateMessage> initialStateListener;
    private final Communicator.Listener<MatchingUpdateMessage> updateListener;
    private final Communicator.Listener<IncomingInviteRequest> incomingInviteListener;

    private Listener listener = null;

    private static volatile MatchingManager instance;

    public static MatchingManager getInstance() {
        if (instance == null) {
            synchronized (MatchingManager.class) {
                if (instance == null) {
                    instance = new MatchingManager();
                }
            }
        }
        return instance;
    }

    private MatchingManager() {
        initialStateListener = (msg) -> {
            if (msg != null) {
                availablePlayers.clear();
                inGamePlayers.clear();
                
                msg.getAvailable().forEach(player -> availablePlayers.put(player.getUsername(), player));
                msg.getInGame().forEach(player -> inGamePlayers.put(player.getUsername(), player));

                listener.onPlayersCollectionsUpdated();
            }
        };

        updateListener = (msg) -> {
            if (msg != null) {
                Map<String, OnlinePlayer> toUpdate;

                switch (msg.getTarget()) {
                    case AVAILABLE:
                        toUpdate = availablePlayers;
                        break;

                    case IN_GAME:
                        toUpdate = inGamePlayers;
                        break;

                    default:
                        return;
                }

                switch (msg.getUpdateType()) {
                    case ADD:
                        toUpdate.put(msg.getPlayer().getUsername(), msg.getPlayer());
                        break;

                    case REMOVE:
                        toUpdate.remove(msg.getPlayer().getUsername());
                        break;

                    default:
                        return;
                }

                listener.onPlayersCollectionsUpdated();
            }
        };

        incomingInviteListener = (msg) -> {
            if (msg != null) {
                listener.onIncomingInviteRequest(msg.getUserName());
            }
        };
    }

    public void setListener(Listener listener) {
        this.listener = listener;

        Communicator.getInstance().setMessageListener(MatchingInitialStateMessage.class, initialStateListener);
        Communicator.getInstance().setMessageListener(MatchingUpdateMessage.class, updateListener);
        Communicator.getInstance().setMessageListener(IncomingInviteRequest.class, incomingInviteListener);

        Communicator.getInstance().sendMessage(new MatchingSubscriptionRequest(true));
    }

    public void unsetListener() {
        listener = null;

        Communicator.getInstance().sendMessage(new MatchingSubscriptionRequest(false));

        Communicator.getInstance().unsetMessageListener(MatchingInitialStateMessage.class);
        Communicator.getInstance().unsetMessageListener(MatchingUpdateMessage.class);
        Communicator.getInstance().unsetMessageListener(IncomingInviteRequest.class);

        availablePlayers.clear();
        inGamePlayers.clear();
    }

    public List<OnlinePlayer> getAvailable() {
        return new ArrayList<>(availablePlayers.values());
    }

    public List<OnlinePlayer> getInGame() {
        return new ArrayList<>(inGamePlayers.values());
    }

    public void invite(OnlinePlayer player) {
        Communicator.getInstance().setMessageListener(InviteResponse.class, (msg) -> {
            if (listener != null) {
                if (msg != null) {

                    listener.onInviteResponse(msg.getResult() == InviteResponse.Result.ACCEPTED,
                        msg.getResult() == InviteResponse.Result.TIMEOUT);
                }
            }
        });

        Communicator.getInstance().sendMessage(new InviteRequest(player.getUsername()));
    }

    public void acceptInvite(OnlinePlayer player) {
        Communicator.getInstance().sendMessage(new IncomingInviteRespose(IncomingInviteRespose.Response.ACCEPTED));

    }

    public void rejectInvite(OnlinePlayer player) {
        Communicator.getInstance().sendMessage(new IncomingInviteRespose(IncomingInviteRespose.Response.REJECTED));
    }

    public interface Listener {

        public void onPlayersCollectionsUpdated();

        public void onIncomingInviteRequest(String userName);

        public void onInviteResponse(boolean accept, boolean timeOut);

        public void onMatchingError(String errorMsg);

    }

}

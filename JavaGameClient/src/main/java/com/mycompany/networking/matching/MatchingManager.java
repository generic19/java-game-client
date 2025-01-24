/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.networking.matching;

import com.mycompany.networking.Communicator;
import com.mycompany.networking.OnlinePlayer;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author ArwaKhaled
 */
public class MatchingManager {
    
  private final Communicator communicator ;

    public MatchingManager(Communicator communicator) {
        
        this.communicator = communicator;
        if(!this.communicator.isConnected()){
            communicator.openConnection();
        }
    }
  
    private Listener listener = null;
    private final Set<OnlinePlayer> availablePlayers = new HashSet<>();
    private final Set<OnlinePlayer> inGamePlayers = new HashSet<>();
    
     
     public void addListener(Listener listener) {
      
        this.listener= listener;
        communicator.sendMessage(new MatchingSubscriptionRequest(true));
        Communicator.Listener<MatchingInitialStateMessage> commuiactorListener =(MatchingInitialStateMessage msg)->{
            
            availablePlayers.addAll(msg.getAvailable());
            inGamePlayers.addAll(msg.getInGame());
            listener.onMatchingUpdate();
        };
        Communicator.Listener<MatchingUpdateMessage> commuiactorUpdateListener=(MatchingUpdateMessage msg)->{
            if(msg == null){
                return;
            }
            if (msg.getTarget()==MatchingUpdateMessage.Target.AVAILABLE){
                if(msg.getUpdateType()==MatchingUpdateMessage.UpdateType.ADD){

                availablePlayers.add(msg.getPlayer());
                }
                else {
                   availablePlayers.remove(msg.getPlayer());
                }
            }else{
                if(msg.getUpdateType()==MatchingUpdateMessage.UpdateType.ADD){
                    inGamePlayers.add(msg.getPlayer());
                }else {
                inGamePlayers.remove(msg.getPlayer());
                }
            }

           listener.onMatchingUpdate();
        };
        communicator.setMessageListener(MatchingInitialStateMessage.class, commuiactorListener);
        communicator.setMessageListener(MatchingUpdateMessage.class, commuiactorUpdateListener);
        
        
        Communicator.Listener<IncomingInviteRequest> incomingInviteRequest = ( IncomingInviteRequest msg)->{
        
           listener.onIncomingInviteRequest(msg.getUserName());
        
        };
        communicator.setMessageListener(IncomingInviteRequest.class, incomingInviteRequest);

    }

    public void removeListener(Listener listener) {
        listener= null;
        communicator.sendMessage(new MatchingSubscriptionRequest(false));
        availablePlayers.clear();
        inGamePlayers.clear();
        
    }

    
    public Set<OnlinePlayer> getAvailable() {
        return new HashSet<>(availablePlayers);
    }

    public Set<OnlinePlayer> getInGame() {
        return new HashSet<>(inGamePlayers);
    }
    
    public void invite(OnlinePlayer player)
    {
        Communicator.Listener<InviteResponse> commListener = (InviteResponse msg)->{
            if(listener!=null){
             if(msg != null){
                
                listener.onInviteResponse(msg.getResult()==InviteResponse.Result.ACCEPTED ,msg.getResult()==InviteResponse.Result.TIMEOUT);
            }else{
                 
             listener.onErrorMessage("error inviting user");
             }
            }
        };
        communicator.setMessageListener(InviteResponse.class, commListener);
        communicator.sendMessage( new InviteRequest(player.getUsername()));
    }
     public void acceptInvite(OnlinePlayer player)
    {
        communicator.sendMessage( new IncomingInviteRespose(IncomingInviteRespose.Response.ACCEPTED));
        
    }
     public void rejectInvite(OnlinePlayer player)
    {
        communicator.sendMessage( new IncomingInviteRespose(IncomingInviteRespose.Response.REJECTED));
    }
    
    
    
    
    
    
    
    public interface Listener {
    public void onMatchingUpdate(); // done
    public void onIncomingInviteRequest(String userName);//
    public void onInviteResponse(boolean accept,boolean timeOut);//done
    public void onErrorMessage(String errorMsg);//done
    
}

}

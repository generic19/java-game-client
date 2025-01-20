/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.networking;

import com.mycompany.networking.matching.IncomingInviteRequest;
import com.mycompany.networking.matching.IncomingInviteRespose;
import com.mycompany.networking.matching.InviteRequest;
import com.mycompany.networking.matching.InviteResponse;
import com.mycompany.networking.matching.MatchingInitialStateMessage;
import com.mycompany.networking.matching.MatchingSubscriptionRequest;
import com.mycompany.networking.matching.MatchingUpdateMessage;
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
        
    }
  
    private Listener listener = null;
    private final Set<OnlinePlayer> availablePlayers = new HashSet<>();
    private final Set<OnlinePlayer> inGamePlayers = new HashSet<>();
    
     
     public void addListener(Listener listener) {
      
        this.listener= listener;
        communicator.sendMessage(new MatchingSubscriptionRequest(true));
        Communicator.Listener<MatchingInitialStateMessage> commuiactorListener =(MatchingInitialStateMessage msg , boolean hasError)->{
            
             availablePlayers.addAll(msg.getAvailable());
             inGamePlayers.addAll(msg.getInGame());
             listener.onMatchingUpdate();
        };
         Communicator.Listener<MatchingUpdateMessage> commuiactorUpdateListener=(MatchingUpdateMessage msg , boolean hasError)->{
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
        communicator.setListener(MatchingInitialStateMessage.class, commuiactorListener);
        communicator.setListener(MatchingUpdateMessage.class, commuiactorUpdateListener);
        
        
        Communicator.Listener<IncomingInviteRequest> incomingInviteRequest = ( IncomingInviteRequest msg , boolean haserror )->{
        
           listener.onIncomingInvite(msg.getUserName());
        
        };
        communicator.setListener(IncomingInviteRequest.class, incomingInviteRequest);

    }

    public void removeListener(Listener listener) {
        listener= null;
        communicator.sendMessage(new MatchingSubscriptionRequest(false));
        availablePlayers.clear();
        inGamePlayers.clear();
        
    }

    
    public Set<OnlinePlayer> getAvailable() {
        return availablePlayers;
    }

    public Set<OnlinePlayer> getInGame() {
        return inGamePlayers ;
    }
    
    public void invite(OnlinePlayer player)
    {
         communicator.setListener(InviteResponse.class, (InviteResponse msg, boolean hasError)->{
            if(listener!=null){
             if(hasError==false){
                
                listener.onIncomingResponse(msg.getResult()==InviteResponse.Result.ACCEPTED ,msg.getResult()==InviteResponse.Result.TIMEOUT);
            }else{
                 
             listener.onErrorMessage("error inviting user");
             }
            }
        });
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
    public void onIncomingInvite(String userName);//
    public void onIncomingResponse(boolean accept,boolean timeOut);//done
    public void onErrorMessage(String errorMsg);//done
    
}

}

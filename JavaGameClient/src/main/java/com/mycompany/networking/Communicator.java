/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
package com.mycompany.networking;

/**
 *
 * @author ayasa
 */
public interface Communicator {
    static Communicator getInstance() {
        return CommunicatorImpl.getInstance();
    }
    
    public <M extends Message> void setMessageListener(Class<M> messageClass, Listener<M> listener);
    public void unsetMessageListener(Class<? extends Message> messageClass);
    
    void addErrorListener(ErrorListener listener);
    void removeErrorListener(ErrorListener listener);
    
    void sendMessage(Message message);
    
    boolean isConnected();
    void openConnection();
    void closeConnection();
    
    interface Listener <M extends Message> {
        void onMessage(M message);
    }
    
    interface ErrorListener {
        void onCommunicatorError(String errorMessage);
    }
}

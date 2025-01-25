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
    static final String DEFAULT_SERVER_ADDRESS = "127.0.0.1";
    static final int SERVER_PORT = 5005;
    
    static Communicator getInstance() {
        return CommunicatorImpl.getInstance();
    }
    
    public <M extends Message> void setMessageListener(Class<M> messageClass, Listener<M> listener);
    public void unsetMessageListener(Class<? extends Message> messageClass);
    
    void setErrorListener(ErrorListener listener);
    void unsetErrorListener();
    
    void setDisconnectedListener(DisconnectedListener listener);
    void unsetDisconnectedListener();
    
    void sendMessage(Message message);
    
    boolean isConnected();
    void openConnection();
    void closeConnection();
    
    void setServerAddress(String ip);
    
    interface Listener <M extends Message> {
        void onMessage(M message);
    }
    
    interface ErrorListener {
        void onCommunicatorError(String errorMessage);
    }
    
    interface DisconnectedListener {
        void onCommunicatorDisconnected();
    }
}

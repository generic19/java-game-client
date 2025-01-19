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
    void setListener(Class type,Listener listener);
    void unsetListener(Class type,Listener listener);
    void sendMessage(Message message);
    void close();
    
    interface Listener <M extends Message> {
        void onMessage(M message,boolean hasError);   
    }
}

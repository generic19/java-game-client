/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.networking.authentication;

import com.mycompany.networking.Communicator;
import com.mycompany.networking.CommunicatorImpl;
import com.mycompany.networking.Message;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author AhmedAli
 */
public class AuthManagerImpl implements AuthManager, Communicator.Listener{
    
    Listener listener;
    Communicator communicator;

    public AuthManagerImpl() {
        communicator = new CommunicatorImpl();
    }
   
    @Override
    public void addListener(Listener listener) {
       this.listener = listener; 
    }

    @Override
    public void removeListener(Listener listener) {
        
    }

    @Override
    public void register(String username, String password) {
        if(isValidData(username, password)){
            communicator.setListener(RegisterRespose.class, this);
            communicator.sendMessage(new RegisterRequest(username, password));
        }
    }

    @Override
    public void signInWithToken() {
        
    }

    @Override
    public void signIn(String username, String password) {
        if(isValidData(username, password)){
            communicator.setListener(SignInResponse.class, this);
            communicator.sendMessage(new SignInRequest(username, password));
        }
    }

    @Override
    public void signOut() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    private boolean isValidData(String username, String password){
        boolean isValid = true;
        
        if(username.trim().isEmpty()){
            isValid = false;
            listener.onError("Username is Empty!\nPlease, Enter your userName");
        }else if(password.trim().isEmpty()){
            isValid = false;
            listener.onError("Password is Empty!\nPlease, Enter your password");
        }else if(username.contains(";")){
            isValid = false;
            listener.onError("Username can not contain special characters");
        }
        
        return isValid;
    
    }

    @Override
    public void onMessage(Message message, boolean hasError) {
        if(hasError){
            listener.onError("server disconnected");
        } else if(message instanceof RegisterRespose){
            
            RegisterRespose response = (RegisterRespose) message;
            if(response.isSuccess()){
                handleSuccessResponse(RegisterRespose.class);
            } else {
                handleErrorRespons(response.getErrorMessage());
                
            }
            
        } else if(message instanceof SignInResponse){
            
            SignInResponse response = (SignInResponse) message;
            if(response.isSuccess()){
                handleSuccessResponse(SignInResponse.class);
                
            } else {
                handleErrorRespons(response.getErrorMessage());
            }
            
        }
    }

    private void handleSuccessResponse(Class type) {
        // TODO: handle saving token with user name in file
        communicator.unsetListener(type, this);
        listener.onAuthStateChange(true);
    }

    private void handleErrorRespons(String errorMessage) {
        listener.onError(errorMessage);
        listener.onAuthStateChange(false);
    }
    
}

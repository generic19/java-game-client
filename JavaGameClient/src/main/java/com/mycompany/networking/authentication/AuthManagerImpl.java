/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.networking.authentication;

import com.mycompany.javagameclient.UIHelper;
import com.mycompany.networking.Communicator;
import com.mycompany.networking.Message;
/**
 *
 * @author AhmedAli
 */
public class AuthManagerImpl implements AuthManager, Communicator.Listener {
    
    Listener listener;
    Communicator communicator;
    String username;

    public AuthManagerImpl() {
        communicator = Communicator.getInstance();
    }
   
    @Override
    public void addListener(Listener listener) {
       this.listener = listener; 
    }

    @Override
    public void removeListener(Listener listener) {
        this.listener = null;
    }

    @Override
    public void register(String username, String password) {
        if(isValidData(username, password)){
            this.username = username;
          //  communicator.setMessageListener(RegisterRespose.class, this);
            communicator.sendMessage(new RegisterRequest(username, password));
        }
    }

    @Override
    public void signInWithToken() {
        /*
        read token & username from the file
        if the token exist ... request to server
        else navigate to login screen
         */
        String token = UIHelper.getToken();
        if(token == null){
            listener.onError("NOT_FOUND");
        } else {
           communicator.setMessageListener(SignInWithTokenResponse.class, this);
           communicator.sendMessage(new SignInWithTokenRequest(token)); 
        }
    }

    @Override
    public void signIn(String username, String password) {
        if(isValidData(username, password)){
            communicator.setMessageListener(SignInResponse.class, this);
            communicator.sendMessage(new SignInRequest(username, password));
        }
    }

    @Override
    public void signOut(String username) {
        communicator.setMessageListener(SignOutRespons.class, this);
       // communicator.sendMessage(new SignOutRequest(username));
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
    public void onMessage(Message message) {
        if(message == null){
            listener.onError("server disconnected");
        } else if(message instanceof RegisterRespose){
           
            RegisterRespose response = (RegisterRespose) message;
            if(response.isSuccess()){
                // token to be saved in file
                saveTokenLocally(response.getToken());
                handleSuccessResponse(RegisterRespose.class);
            } else {
                handleErrorRespons(response.getErrorMessage());
            }
        } else if(message instanceof SignInResponse){
            
            SignInResponse response = (SignInResponse) message;
            if(response.isSuccess()){
                // token to be saved in file
                saveTokenLocally(response.getToken());
                handleSuccessResponse(SignInResponse.class);
            } else {
                handleErrorRespons(response.getErrorMessage());
            }
        } else if(message instanceof SignInWithTokenResponse ){
            
            SignInWithTokenResponse response = (SignInWithTokenResponse) message;
            
            if(response.isSuccess()){
                handleSuccessResponse(SignInWithTokenResponse.class);
            } else{
                listener.onAuthStateChange(false);
            }
        } else if (message instanceof SignOutRespons){
            SignOutRespons response = (SignOutRespons) message;
            
            if(response.isSuccess()){
                listener.onAuthStateChange(true);
            } else {
                listener.onError("Logout Failed");
            }
        }
    }

    private void handleSuccessResponse(Class type) {
        communicator.unsetMessageListener(type);
        listener.onAuthStateChange(true);
    }

    private void handleErrorRespons(String errorMessage) {
        listener.onError(errorMessage);
        listener.onAuthStateChange(false);
    }

    private void saveTokenLocally(String token) {
        UIHelper.saveTokenIntoFile(username, token);
    }    
}

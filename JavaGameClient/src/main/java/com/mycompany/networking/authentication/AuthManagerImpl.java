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
public class AuthManagerImpl implements AuthManager {

    Listener listener;
    Communicator communicator;
    String username;

    private static AuthManagerImpl instance;
    
    public static AuthManagerImpl getInstance() {
        if (instance == null) {
            synchronized (AuthManagerImpl.class) {
                if (instance == null) {
                    instance = new AuthManagerImpl();
                }
            }
        }
        
        return instance;
    }
    
    private AuthManagerImpl() {
        communicator = Communicator.getInstance();
    }

    @Override
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void unsetListener() {
        this.listener = null;
    }

    @Override
    public void register(String username, String password) {
        if (isValidData(username, password)) {
            this.username = username;

            communicator.setMessageListener(RegisterRespose.class, (message) -> {
                if (message != null) {
                    if (message.isSuccess()) {
                        // token to be saved in file
                        saveTokenLocally(message.getToken());
                        handleSuccessResponse(RegisterRespose.class);
                    } else {
                        handleErrorRespons(message.getErrorMessage());
                    }
                }

                communicator.unsetMessageListener(RegisterRespose.class);
            });

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
        
        if (token == null) {
            listener.onError("Session token not found.");
        } 
        else {
            communicator.setMessageListener(SignInWithTokenResponse.class, (response) -> {
                if (response != null) {
                    if (response.isSuccess()) {
                        handleSuccessResponse(SignInWithTokenResponse.class);
                    } else {
                        listener.onAuthStateChange(false);
                    }
                }

                communicator.unsetMessageListener(SignInWithTokenResponse.class);
            });
            
            communicator.sendMessage(new SignInWithTokenRequest(token));
        }
    }

    @Override
    public void signIn(String username, String password) {
        if (isValidData(username, password)) {
            communicator.setMessageListener(SignInResponse.class, (response) -> {
                if (response != null) {
                    if (response.isSuccess()) {
                        // token to be saved in file
                        saveTokenLocally(response.getToken());
                        handleSuccessResponse(SignInResponse.class);
                    } else {
                        handleErrorRespons(response.getErrorMessage());
                    }
                }

                communicator.unsetMessageListener(SignInResponse.class);
            });

            communicator.sendMessage(new SignInRequest(username, password));
        }
    }

    @Override
    public void signOut() {
        communicator.setMessageListener(SignOutRespons.class, (response) -> {
            if (response.isSuccess()) {
                listener.onAuthStateChange(false);
            } else {
                listener.onError("Logout Failed");
            }

            communicator.unsetMessageListener(SignOutRespons.class);
        });
        
        communicator.sendMessage(new SignOutRequest());
    }

    private boolean isValidData(String username, String password) {
        boolean isValid = true;

        if (username.trim().isEmpty()) {
            isValid = false;
            listener.onError("Username is Empty!\nPlease, Enter your userName");
        } else if (password.trim().isEmpty()) {
            isValid = false;
            listener.onError("Password is Empty!\nPlease, Enter your password");
        } else if (username.contains(";")) {
            isValid = false;
            listener.onError("Username can not contain special characters");
        }

        return isValid;

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

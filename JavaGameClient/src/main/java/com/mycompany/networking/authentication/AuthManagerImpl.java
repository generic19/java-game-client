/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.networking.authentication;

import com.mycompany.javagameclient.UIHelper;
import com.mycompany.networking.Communicator;

/**
 *
 * @author AhmedAli
 */
public class AuthManagerImpl implements AuthManager {

    private Listener listener;
    private String username;

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
            Communicator.getInstance().setMessageListener(RegisterRespose.class, (message) -> {
                if (message != null) {
                    if (message.isSuccess()) {
                        this.username = username;
                        // token to be saved in file
                        saveTokenLocally(message.getToken());
                        listener.onAuthStateChange(true);
                    } else {
                        this.username = null;
                        listener.onAuthError(message.getErrorMessage());
                    }
                } else {
                    this.username = null;
                }

                Communicator.getInstance().unsetMessageListener(RegisterRespose.class);
            });

            Communicator.getInstance().sendMessage(new RegisterRequest(username, password));
        }
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void signInWithToken() {
        /*
        read token & username from the file
        if the token exist ... request to server
        else navigate to login screen
         */
        UIHelper.TokenData tokenData = UIHelper.getToken();

        if (tokenData.getToken() == null) {
            this.username = null;
            throw new IllegalStateException("Session token is null.");
        } else {
            Communicator.getInstance().setMessageListener(SignInWithTokenResponse.class, (response) -> {
                if (response != null) {
                    if (response.isSuccess()) {
                        this.username = tokenData.getUsername();
                        listener.onAuthStateChange(true);
                    } else {
                        this.username = null;
                        listener.onAuthStateChange(false);
                    }
                } else {
                    this.username = null;
                    listener.onAuthStateChange(false);
                }

                Communicator.getInstance().unsetMessageListener(SignInWithTokenResponse.class);
            });

            Communicator.getInstance().sendMessage(new SignInWithTokenRequest(tokenData.getToken()));
        }
    }

    @Override
    public void signIn(String username, String password) {
        if (isValidData(username, password)) {
            Communicator.getInstance().setMessageListener(SignInResponse.class, (response) -> {
                if (response != null) {
                    if (response.isSuccess()) {
                        this.username = username;
                        // token to be saved in file
                        saveTokenLocally(response.getToken());
                        listener.onAuthStateChange(true);
                    } else {
                        this.username = null;
                        listener.onAuthError(response.getErrorMessage());
                    }
                } else {
                    this.username = null;
                    listener.onAuthStateChange(false);
                }

                Communicator.getInstance().unsetMessageListener(SignInResponse.class);
            });

            Communicator.getInstance().sendMessage(new SignInRequest(username, password));
        }
    }

    @Override
    public void signOut() {
        Communicator.getInstance().setMessageListener(SignOutRespons.class, (response) -> {
            if (response.isSuccess()) {
                UIHelper.deleteTokenFile();
                this.username = null;
                listener.onAuthStateChange(false);
            } else {
                listener.onAuthError("Could not log out.");
            }
            Communicator.getInstance().unsetMessageListener(SignOutRespons.class);
        });

        Communicator.getInstance().sendMessage(new SignOutRequest());
    }

    private boolean isValidData(String username, String password) {
        boolean isValid = true;

        if (username.trim().isEmpty()) {
            isValid = false;
            listener.onAuthError("Username is Empty!\nPlease, Enter your userName");
        } else if (password.trim().isEmpty()) {
            isValid = false;
            listener.onAuthError("Password is Empty!\nPlease, Enter your password");
        } else if (username.contains(";")) {
            isValid = false;
            listener.onAuthError("Username can not contain special characters");
        }

        return isValid;
    }

    private void saveTokenLocally(String token) {
        UIHelper.saveTokenIntoFile(username, token);
    }
}

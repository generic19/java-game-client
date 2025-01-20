/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.networking.authentication;

/**
 *
 * @author AhmedAli
 */
public interface AuthManager {
    
    void addListener(Listener listener);
    void removeListener(Listener listener);
    void register(String username, String password);
    void signInWithToken();
    void signIn(String username, String password);
    void signOut();
    
    interface Listener{
        void onAuthStateChange(boolean signedIn);
        void onError(String errorMsg);
    }
}

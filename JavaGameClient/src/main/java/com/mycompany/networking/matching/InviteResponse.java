/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.networking.matching;

/**
 *
 * @author AhmedAli
 */
public class InviteResponse implements MatchingMessage {
    
    public enum Result{
        ACCEPTED, REJECTED, TIMEOUT;
    }
}

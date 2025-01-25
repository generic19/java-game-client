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
    private Result result ;

    public InviteResponse(Result result) {
        this.result = result;
    }

    public Result getResult() {
        return result;
    }
    
    public enum Result{
        ACCEPTED, REJECTED, TIMEOUT;
    }

    @Override
    public String toString() {
        return "InviteResponse{" + "result=" + result + '}';
    }
}

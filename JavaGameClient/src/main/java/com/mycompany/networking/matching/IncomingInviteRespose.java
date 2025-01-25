/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.networking.matching;

/**
 *
 * @author ArwaKhaled
 */
public class IncomingInviteRespose implements MatchingMessage {
    
    private final Response response ;

    public Response getResponse() {
        return response;
    }

    public IncomingInviteRespose(Response response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "IncomingInviteRespose{" + "response=" + response + '}';
    }
    
    public enum Response{
        ACCEPTED, REJECTED;
    }
}

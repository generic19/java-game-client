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
    
    private Response response ;

    public Response getResponse() {
        return response;
    }

    public IncomingInviteRespose(Response response) {
        this.response = response;
    }
    public enum Response{
        ACCEPTED, REJECTED;
    }
}

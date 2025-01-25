/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.networking.matching;

/**
 *
 * @author basel
 */
public class MatchingSubscriptionRequest implements MatchingMessage {
    private final boolean subscribe;

    public MatchingSubscriptionRequest(boolean subscribe) {
        this.subscribe = subscribe;
    }

    public boolean isSubscribe() {
        return subscribe;
    }

    @Override
    public String toString() {
        return "MatchingSubscriptionRequest{" + "subscribe=" + subscribe + '}';
    }
}

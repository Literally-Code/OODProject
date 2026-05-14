package com.wss.items;

public class TradeResponse {
    private boolean accepted;
    private TradeOffer counterOffer;
    private String message;

    public TradeResponse(boolean accepted, TradeOffer counterOffer, String message) {
        this.accepted = accepted;
        this.counterOffer = counterOffer;
        this.message = message;
    }

    public boolean isAccepted() { return accepted; }
    public TradeOffer getCounterOffer() { return counterOffer; }
    public String getMessage() { return message; }
}


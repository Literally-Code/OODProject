package com.wss.items;

public class Trader {
    protected String name;
    protected int patience;
    protected int counterCount;

    public Trader(String name, int patience) {
        this.name = name;
        this.patience = patience;
        this.counterCount = 0;
    }

    public TradeResponse evaluateOffer(TradeOffer offer) {
        if (counterCount >= patience) {
            return new TradeResponse(false, null, "Trader rejects and stops negotiating.");
        }

        if (isFairTrade(offer)) {
            return new TradeResponse(true, offer, "Trader accepts the offer.");
        }

        counterCount++;
        TradeOffer counterOffer = makeCounterOffer(offer);
        return new TradeResponse(false, counterOffer, "Trader makes a counter offer.");
    }

    protected boolean isFairTrade(TradeOffer offer) {
        int playerGivesValue =
                offer.getGoldOffered() * 3 +
                offer.getFoodOffered() * 2 +
                offer.getWaterOffered() * 2;

        int playerRequestsValue =
                offer.getGoldRequested() * 3 +
                offer.getFoodRequested() * 2 +
                offer.getWaterRequested() * 2;

        return playerGivesValue >= playerRequestsValue;
    }

    protected TradeOffer makeCounterOffer(TradeOffer offer) {
        return new TradeOffer(
                offer.getGoldOffered() + 1,
                offer.getFoodOffered(),
                offer.getWaterOffered(),
                offer.getGoldRequested(),
                offer.getFoodRequested(),
                offer.getWaterRequested()
        );
    }

    public void resetNegotiation() {
        counterCount = 0;
    }
}
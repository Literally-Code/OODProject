package com.wss.items;

public class GreedyTrader extends Trader {

    public GreedyTrader() {
        super("Greedy Trader", 3);
    }

    @Override
    protected boolean isFairTrade(TradeOffer offer) {
        int playerGives =
                offer.getGoldOffered() * 3 +
                offer.getFoodOffered() * 2 +
                offer.getWaterOffered() * 2;

        int playerWants =
                offer.getGoldRequested() * 3 +
                offer.getFoodRequested() * 2 +
                offer.getWaterRequested() * 2;

        return playerGives >= playerWants * 2;
    }
}
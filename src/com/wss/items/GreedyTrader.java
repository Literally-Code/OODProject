package com.wss.items;

public class GreedyTrader extends Trader {

    public GreedyTrader() {
        super(8, 4);
        this.desiredGold += 30;
    }

    @Override
    public TradeOffer counterTrade(TradeOffer offer) {
        System.out.println("Greedy Trader demands more.");

        return new TradeOffer(
                offer.getGoldOffered() + 2,
                offer.getFoodOffered(),
                offer.getWaterOffered(),
                offer.getGoldRequested(),
                offer.getFoodRequested(),
                offer.getWaterRequested()
        );
    }
}
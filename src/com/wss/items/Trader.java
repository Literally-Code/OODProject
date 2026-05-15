package com.wss.items;

public class Trader extends Items {
    protected int interest;
    protected int dangerLevel;

    public Trader(int interest, int dangerLevel) {
        super("Trader", true);
        this.interest = interest;
        this.dangerLevel = dangerLevel;
        this.sprite = "T";
    }

    public TradeOffer counterTrade(TradeOffer offer) {
        System.out.println("Trader makes a counter offer.");

        return new TradeOffer(
                offer.getGoldOffered() + 1,
                offer.getFoodOffered(),
                offer.getWaterOffered(),
                offer.getGoldRequested(),
                offer.getFoodRequested(),
                offer.getWaterRequested()
        );
    }
    public Trader() {
        super("Trader", true);
        this.interest = 5;
        this.dangerLevel = 3;
        this.sprite = "T";
    }

    public void acceptTrade() {
        System.out.println("Trader accepts the trade.");
    }

    public void rejectTrade() {
        System.out.println("Trader rejects the trade.");
    }

    public boolean stab() {
        int chance = (int) (Math.random() * 10);
        return chance < dangerLevel;
    }

    public int getInterest() {
        return interest;
    }

    public int getDangerLevel() {
        return dangerLevel;
    }
}
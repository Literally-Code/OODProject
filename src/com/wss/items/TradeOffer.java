package com.wss.items;

public class TradeOffer {
    private int goldOffered, foodOffered, waterOffered;
    private int goldRequested, foodRequested, waterRequested;

    public TradeOffer(int goldOffered, int foodOffered, int waterOffered,
                      int goldRequested, int foodRequested, int waterRequested) {
        this.goldOffered = goldOffered;
        this.foodOffered = foodOffered;
        this.waterOffered = waterOffered;
        this.goldRequested = goldRequested;
        this.foodRequested = foodRequested;
        this.waterRequested = waterRequested;
    }

    public int getGoldOffered() { return goldOffered; }
    public int getFoodOffered() { return foodOffered; }
    public int getWaterOffered() { return waterOffered; }

    public int getGoldRequested() { return goldRequested; }
    public int getFoodRequested() { return foodRequested; }
    public int getWaterRequested() { return waterRequested; }
}
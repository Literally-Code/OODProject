package com.wss.items;

import java.util.Random;

import com.wss.player.Player;

public class Trader extends Items {
    protected int interest;
    protected int dangerLevel;
    
    protected int desiredGold;
    protected int desiredFood;
    protected int desiredWater;

    protected int maxGold;
    protected int maxFood;
    protected int maxWater;

    public Trader(int interest, int dangerLevel) {
        super("Trader", true);
        this.interest = interest;
        this.dangerLevel = dangerLevel;
        this.sprite = "T";
        this.initRandomValues();
    }

    public Trader() {
        super("Trader", true);
        Random random = new Random();

        this.interest = random.nextInt(8);
        this.dangerLevel = random.nextInt(8);
        this.sprite = "T";
        this.initRandomValues();
    }

    public void initRandomValues()
    {
        Random random = new Random();

        this.maxGold = random.nextInt(100) + 1;
        this.maxFood = random.nextInt(100) + 1;
        this.maxWater = random.nextInt(100) + 1;

        // Trader will want more of what they don't have
        this.desiredGold = 100 - this.maxGold + (random.nextInt(10) - 5);
        this.desiredGold = this.desiredGold < 0 ? 0 : this.desiredGold;

        this.desiredFood = 100 - this.maxFood + (random.nextInt(10) - 5);
        this.desiredFood = this.desiredFood < 0 ? 0 : this.desiredFood;

        this.desiredWater = 100 - this.maxWater + (random.nextInt(10) - 5);
        this.desiredWater = this.desiredWater < 0 ? 0 : this.desiredWater;
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

    public boolean sendOffer(Player player, TradeOffer offer)
    {
        System.out.println("Sent offer to trader");

        if (this.dangerLevel >= 10)
        {
            this.rejectTrade();
            System.out.println("The trader grows annoyed at your incessance and attacks you.");
            player.rejectTrade(this);
            this.interest = 0;
            player.addStrength(-10); // Stab the player
            return false;
        }

        if (this.interest <= 3)
        {
            this.rejectTrade();
            System.out.println("The trader is no longer interested, and is demanding you to leave.");
            player.rejectTrade(this);
            this.dangerLevel++;
            return false;
        }

        if (!this.canAcceptOffer(offer) && (this.interest < 10))
        {
            this.rejectTrade();
            player.rejectTrade(this);
            Random random = new Random();
            this.interest += random.nextInt(2) - 1; // +- 1 interest per offer
            this.dangerLevel++; // Always increase danger level
            // If the offer is less than what the trader desires, have a chance to hint at what the trader wants
            if (offer.getGoldOffered() < this.desiredGold && (random.nextDouble() < 0.25))
            {
                System.out.println("The trader seems to want more gold...");
            }
            if (offer.getFoodOffered() < this.desiredFood && (random.nextDouble() < 0.25))
            {
                System.out.println("The trader seems to want more food...");
            }
            if (offer.getWaterOffered() < this.desiredWater && (random.nextDouble() < 0.25))
            {
                System.out.println("The trader seems to want more water...");
            }
            return false;
        }

        this.acceptTrade();
            
        player.addWater(offer.getGoldRequested());
        player.addFood(offer.getGoldRequested());
        player.addGold(offer.getGoldRequested());

        player.addWater(-offer.getGoldOffered());
        player.addFood(-offer.getFoodOffered());
        player.addGold(-offer.getGoldOffered());

        return true;
    }

    public boolean canAcceptOffer(TradeOffer offer)
    {
        return (offer.getGoldOffered() >= this.desiredGold && 
            offer.getFoodOffered() >= this.desiredFood && 
            offer.getWaterOffered() >= this.desiredWater) &&
            
            (offer.getGoldRequested() <= this.maxGold &&
            offer.getFoodRequested() <= this.maxFood &&
            offer.getWaterRequested() <= this.maxWater);
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

    public int getMaxFood()
    {
        return this.maxFood;
    }
    
    public int getMaxGold()
    {
        return this.maxGold;
    }

    public int getMaxWater()
    {
        return this.maxWater;
    }

    public int getInterest() {
        return interest;
    }

    public int getDangerLevel() {
        return dangerLevel;
    }
}
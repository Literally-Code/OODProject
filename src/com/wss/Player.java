package com.wss;
import java.util.Scanner;

/*
*
*   If the Food and Water class's bonus values aren't its values then this might need some fixing :grimace:
*
*/

public class Player {
    private int currStrength, currWater, currFood, gold;
    private int maxStrength, maxWater, maxFood;
    private Vision eye;
    private Brain brain;

    //By default, the player is a Standard player
    public Player(Vision eye, Brain brain)
    {
        maxStrength = 100;
        maxWater = 100;
        maxFood = 100;

        currStrength = 100;
        currWater = 100;
        currFood = 100;
    }

    //I haven't figured this one out yet o_o
    public void initialize()
    {

    }

    /*
    *   suggestTrade() asks the player for the amount of food and water they want to receive and the price (gold) they
    *   are willing to pay for it. Returns the trade offer as a Trade object
    */
    public Trade suggestTrade()
    {
        Scanner scnr = new Scanner(System.in);
        float foodOffer, waterOffer, goldOffer;
        Trade offer = null;

        System.out.println("Suggesting a Trade... Please enter the amount of each item you want to receive: ");
        
        System.out.print("Food: ");
        foodOffer = scnr.nextFloat();

        System.out.print("Water: ");
        waterOffer = scnr.nextFloat();

        System.out.println("Please enter the amount of gold you are offering: ");
        goldOffer = scnr.nextInt();
        if(gold < goldOffer)
            System.out.print("Insufficient gold. Try another value: ");
        //If the player's current gold value is less than the amount of gold they're offering, continue to prompt until valid
        while(gold < goldOffer)         
        {                                   
            goldOffer = scnr.nextInt();
            if(gold < goldOffer)
                System.out.print("Insufficient gold. Try another value: ");
        }

        Items[] tradeOffer = new Items[2];
        //Food foodTrade = new Food(foodOffer);
        //Water waterTrade = new Water(waterOffer);

        //offer = new Trade(goldOffer, tradeOffer);

        scnr.close();
        return offer;

    }

    //acceptTrade() calls collectBonus to collect items from a trade
    public void acceptTrade(Trade offer)
    {
        //Get the Item array from offer and gain the bonuses of each item in the array
    }

    //rejectTrade asks if the player wants to suggest a counteroffer after rejecting. Returns true if the player wants to counter 
    public boolean rejectTrade()
    {
        Scanner scnr = new Scanner(System.in);
        String counter;

        System.out.println("Trade Rejected. Suggest a counteroffer? (y/n): ");
        counter = scnr.next();
        scnr.close();

        if(counter.equalsIgnoreCase("y"))
            return true;
        else if(counter.equalsIgnoreCase("n"))
            return false;
        else
            return false;
    }

    //it's in the name
    public void collectBonus(Items bonus)
    {
        //wait how will it distinguish between item types.....
        float someArbitraryValue = 0; // item bonus value
        if(someArbitraryValue + currFood > maxFood)
            currFood = maxFood;
        if(someArbitraryValue + currWater > maxWater)
            currWater = maxWater;
        
        gold = (int) someArbitraryValue;
            
    }
    
    //makeMove() calls makeMove() in the Brain object
    public Path makeMove()
    {
        Path somePathObject = brain.makeMove(eye);
        //if the path = 0
            if(currStrength + 5 > maxStrength)
                currStrength = maxStrength;
            else
                currStrength += 5; //Or some value idk yet

        return somePathObject;
    }

}

package com.wss.player;

import com.wss.Trade;
import com.wss.items.Food;
import com.wss.items.Gold;
import com.wss.items.Items;
import com.wss.items.TradeOffer;
import com.wss.items.Trader;
import com.wss.items.Water;
import com.wss.map.MapGrid;
import com.wss.map.Square;
import com.wss.player.vision.*;
import com.wss.spacial.Path;
import com.wss.spacial.Position;
import java.util.Scanner;

public class Player {

    private int currStrength;

    private int currWater;

    private int currFood;

    private int gold;

    protected int maxStrength;

    protected int maxWater;

    protected int maxFood;

    private Vision eye;

    private Brain brain;

    private MapGrid map;

    private Position initialPos;

    private Position currentPos;

    private Position previousPos;

    private Scanner scnr =
        new Scanner(System.in);

    public Player()
    {

    }

    public Player(
        int str,
        int water,
        int food
    )
    {
        maxStrength = str;

        maxWater = water;

        maxFood = food;

        currStrength = maxStrength;

        currWater = maxWater;

        currFood = maxFood;

        gold = 5;
    }

    public void initialize(
        MapGrid map,
        Brain brain,
        Vision vision
    )
    {
        this.map = map;

        initialPos =
            new Position(0, 0);

        currentPos =
            initialPos;

        previousPos = null;

        eye = vision;

        this.brain = brain;

        eye.setPlayerPosition(currentPos);
    }

    public TradeOffer promptTradeOffer()
    {
        int foodOffer;
        int waterOffer;
        int goldOffer;

        int foodRequest;
        int waterRequest;
        int goldRequest;

        System.out.println(
            "Suggesting a Trade..."
        );

        System.out.println(
            "Please enter the amount of each item you want to offer:"
        );

        do {
            System.out.print("Food: ");
            foodOffer =
                scnr.nextInt(); 
                
            if (foodOffer > this.currFood)
            {
                System.out.println("Insufficient food.");
            }
        } while (foodOffer > this.currFood);

        do {
            System.out.print("Water: ");
            waterOffer =
                scnr.nextInt(); 
                
            if (waterOffer > this.currWater)
            {
                System.out.println("Insufficient water.");
            }
        } while (waterOffer > this.currWater);

        do {
            System.out.print("Gold: ");
            goldOffer =
                scnr.nextInt(); 
                
            if (goldOffer > this.gold)
            {
                System.out.println("Insufficient gold.");
            }
        } while (goldOffer > this.gold);


        System.out.println(
            "Please enter the amount of each item you want to receive:"
        );

        System.out.print("Food: ");
        foodRequest = scnr.nextInt();
        
        System.out.print("Water: ");
        waterRequest = scnr.nextInt();
        
        System.out.print("Gold: ");
        goldRequest = scnr.nextInt();
        
        TradeOffer offer = new TradeOffer(goldOffer, foodOffer, waterOffer, goldRequest, foodRequest, waterRequest);

        return offer;
    }

    public void acceptTrade(Trade offer)
    {

    }

    public void rejectTrade(Trader trader)
    {
        System.out.println(
            "Trade Rejected. Suggest a counteroffer? (y/n): "
        );
    }

    public void collectBonus(Items bonus)
    {
        applyItem(bonus);
    }

    /*
     * AI movement
     */

    public Path makeMove()
    {
        if(brain == null || eye == null)
        {
            System.out.println(
                "Brain or Vision not initialized."
            );

            return null;
        }

        if(!isAlive())
        {
            System.out.println(
                "Player can no longer continue."
            );

            return null;
        }

        eye.setPlayerPosition(currentPos);

        int[] stats = {
            currStrength,
            currWater,
            currFood,
            gold
        };

        Path path =
            brain.makeMove(eye, stats);

        if(path == null)
        {
            rest();

            return null;
        }

        if(
            path.getMoves() == null ||
            path.getMoves().isEmpty()
        )
        {
            System.out.println(
                "No valid moves found."
            );

            return null;
        }

        Position nextMove =
            path.getMoves().get(0);

        if(
            previousPos != null &&
            nextMove.getRow() ==
                previousPos.getRow() &&
            nextMove.getCol() ==
                previousPos.getCol()
        )
        {
            Path alternatePath =
                eye.easiestPath();

            if(
                alternatePath != null &&
                alternatePath.getMoves() != null &&
                !alternatePath.getMoves().isEmpty()
            )
            {
                nextMove =
                    alternatePath
                        .getMoves()
                        .get(0);
            }
        }

        moveTo(nextMove);

        return path;
    }

    /*
     * Manual player movement
     */

    public Path makeManualMove(
        Scanner input
    )
    {
        if(!isAlive())
        {
            System.out.println(
                "Player can no longer continue."
            );

            return null;
        }

        System.out.print(
            "Enter move (W/A/S/D or R to rest): "
        );

        String choice =
            input.next();

        /*
         * Manual resting
         */

        if(choice.equalsIgnoreCase("r"))
        {
            rest();

            return null;
        }

        int nextRow =
            currentPos.getRow();

        int nextCol =
            currentPos.getCol();

        if(choice.equalsIgnoreCase("w"))
        {
            nextRow--;
        }
        else if(choice.equalsIgnoreCase("s"))
        {
            nextRow++;
        }
        else if(choice.equalsIgnoreCase("a"))
        {
            nextCol--;
        }
        else if(choice.equalsIgnoreCase("d"))
        {
            nextCol++;
        }
        else
        {
            System.out.println(
                "Invalid move. Player rests instead."
            );

            rest();

            return null;
        }

        if(!isValidPosition(nextRow, nextCol))
        {
            System.out.println(
                "Cannot move outside the map. Player rests instead."
            );

            rest();

            return null;
        }

        Position nextMove =
            new Position(
                nextRow,
                nextCol
            );

        Path path =
            new Path();

        path.addMove(nextMove);

        moveTo(nextMove);

        return path;
    }

    private boolean isValidPosition(
        int row,
        int col
    )
    {
        return row >= 0 &&
               row < map.getSize().getHeight() &&
               col >= 0 &&
               col < map.getSize().getWidth();
    }

    private void moveTo(Position nextMove)
    {
        previousPos =
            currentPos;

        currentPos =
            nextMove;

        eye.setPlayerPosition(currentPos);

        applyTerrainCost();

        collectItemsOnCurrentSquare();

        System.out.println(
            "Player moved to: " + currentPos
        );

        printStats();
    }

    private void applyTerrainCost()
    {
        Square square =
            map.getSquare(currentPos);

        currStrength -=
            square.getStrengthCost();

        currFood -=
            square.getFoodCost();

        int waterCost =
            square.getWaterCost();

        if(waterCost < 0)
        {
            currWater =
                Math.min(
                    maxWater,
                    currWater + Math.abs(waterCost)
                );
        }
        else
        {
            currWater -= waterCost;
        }

        clampStats();
    }

    private void collectItemsOnCurrentSquare()
    {
        Square square =
            map.getSquare(currentPos);

        if(!square.hasItems())
        {
            return;
        }

        Items[] collectedItems =
            square.collectItems();

        for(Items item : collectedItems)
        {
            applyItem(item);
        }
    }

    private void applyItem(Items item)
    {
        if(item instanceof Food)
        {
            Food food =
                (Food)item;

            currFood =
                Math.min(
                    maxFood,
                    currFood + food.getAmount()
                );

            System.out.println(
                "Collected food. Food is now " +
                currFood + "."
            );
        }
        else if(item instanceof Water)
        {
            Water water =
                (Water)item;

            currWater =
                Math.min(
                    maxWater,
                    currWater + water.getAmount()
                );

            System.out.println(
                "Collected water. Water is now " +
                currWater + "."
            );
        }
        else if(item instanceof Gold)
        {
            Gold collectedGold =
                (Gold)item;

            gold +=
                collectedGold.getAmount();

            System.out.println(
                "Collected gold. Gold is now " +
                gold + "."
            );
        }
        else if(item instanceof Trader)
        {
            boolean continueTrading = true; 
            
            while (continueTrading && this.currStrength > 10)
            {
                Trader trader = (Trader)item;
                System.out.println(String.format("This trader has %d gold, %d food and %d water", trader.getMaxGold(), trader.getMaxFood(), trader.getMaxWater()));
                boolean tradeSuccess = this.tradeWithTrader(trader);
                continueTrading = false;

                if (!tradeSuccess)
                {
                    String counter;

                    counter =
                        scnr.next();

                    if (counter.equalsIgnoreCase("y"))
                    {
                        continueTrading = true;
                    }
                }
            }
        }
    }

    private boolean tradeWithTrader(Trader trader)
    {
        TradeOffer offer = promptTradeOffer();

        return trader.sendOffer(this, offer);
    }

    /*
     * Resting restores strength
     * but consumes food/water.
     */

    private void rest()
    {
        currStrength =
            Math.min(
                maxStrength,
                currStrength + 10
            );

        currFood -= 1;

        currWater -= 1;

        clampStats();

        System.out.println(
            "Player is resting."
        );

        printStats();
    }

    private void clampStats()
    {
        currStrength =
            Math.max(
                0,
                Math.min(maxStrength, currStrength)
            );

        currFood =
            Math.max(
                0,
                Math.min(maxFood, currFood)
            );

        currWater =
            Math.max(
                0,
                Math.min(maxWater, currWater)
            );
    }

    public boolean isAlive()
    {
        return currStrength > 0 &&
               currFood > 0 &&
               currWater > 0;
    }

    public void printStats()
    {
        System.out.println(
            "Stats -> Strength: " +
            currStrength +
            ", Water: " +
            currWater +
            ", Food: " +
            currFood +
            ", Gold: " +
            gold
        );
    }

    public Position getCurrentPosition()
    {
        return currentPos;
    }

    public void setStrength(int value)
    {
        currStrength = value;
    }

    public void addStrength(int value)
    {
        currStrength += value;
    }

    public void setWater(int value)
    {
        currWater = value;
    }

    public void setFood(int value)
    {
        currFood = value;
    }

    public void setGold(int value)
    {
        gold = value;
    }

    public void addWater(int value)
    {
        currWater += value;
    }

    public void addFood(int value)
    {
        currFood += value;
    }

    public void addGold(int value)
    {
        gold += value;
    }

    public void setVision(Vision vision)
    {
        eye = vision;
    }

    public void setBrain(Brain brain)
    {
        this.brain = brain;
    }
}
package com.wss.player;

import com.wss.player.vision.Vision;
import com.wss.spacial.Path;

public class BalancedBrain extends Brain {

    public BalancedBrain()
    {

    }

    @Override
    public Path makeMove(Vision eye, int[] stats)
    {
        int strength = stats[0];
        int water = stats[1];
        int food = stats[2];
        int gold = stats[3];

        if(strength < 20)
        {
            return null;
        }

        if(water < 40)
        {
            Path waterPath = eye.closestWater();

            if(waterPath != null)
            {
                return waterPath;
            }

            Path secondWaterPath = eye.secondClosestWater();

            if(secondWaterPath != null)
            {
                return secondWaterPath;
            }
        }

        if(food < 40)
        {
            Path foodPath = eye.closestFood();

            if(foodPath != null)
            {
                return foodPath;
            }

            Path secondFoodPath = eye.secondClosestFood();

            if(secondFoodPath != null)
            {
                return secondFoodPath;
            }
        }

        if(gold >= 5 && (water < 70 || food < 70))
        {
            Path traderPath = eye.closestTrader();

            if(traderPath != null)
            {
                return traderPath;
            }
        }

        if(gold < 5)
        {
            Path goldPath = eye.closestGold();

            if(goldPath != null)
            {
                return goldPath;
            }
        }

        return eye.easiestPath();
    }
}
package com.wss.player;

import com.wss.player.vision.Vision;
import com.wss.spacial.Path;


public class BalancedBrain extends Brain{
    public BalancedBrain()
    {}

    //prioritizes maximum resource gain and minimal resource loss
    public Path makeMove(Vision eye, int[] stats, int[] maxStats)
    {
        int leastResource = 100;
        int resourceIndex = -1;
        //Omit the gold stat
        for(int i = 0; i < stats.length-1; i++)
        {
            if (stats[i] < leastResource && (stats[i] != maxStats[i]))
            {
                leastResource = stats[i];
                resourceIndex = i;
            }
        }

        //If there isn't a need for resources, find a trader
        if(leastResource == 100)
        {
            //Not definitely necessary, so no need to find second closest trader
            Path trader = eye.closestTrader();
            if(trader != null)
                return trader;
        }

        //0 = strength, 1 = water, 2 = food
        //If strength is the lowest, rest
        if(resourceIndex == 0)
            return null;
        //If water is the lowest, find closest/second closest water
        else if (resourceIndex == 1)
        {
            Path waterPath = eye.closestWater();
            Path waterPath2 = eye.secondClosestWater();

            if(waterPath != null)
                return waterPath;

            if(waterPath2 != null)
                return waterPath2;
        //If food is lowest, find closest/second closest food
        }else if (resourceIndex == 2)
        {
            Path foodPath = eye.closestFood();
            Path foodPath2 = eye.secondClosestFood();

            if(foodPath != null)
                return foodPath;

            if(foodPath2 != null)
                return foodPath2;
        }
        //If no resources are needed, continue moving
        else
            return eye.easiestPath();
        
        //rest if no path is found
        return null;

    }
}

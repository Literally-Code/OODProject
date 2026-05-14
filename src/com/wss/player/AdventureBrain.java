package com.wss.player;

import com.wss.player.vision.Vision;
import com.wss.spacial.Path;

public class AdventureBrain {
    public AdventureBrain()
    {}

    //prioritizes moving east, doesn't like to stay in one spot
    public Path makeMove(Vision eye, int[] stats)
    {
        //If strength gets low enough, player must rest
        if(stats[0] < 15)
            return null;

        int sufficient = 0;
        //Omit gold
        for(int i = 0; i < stats.length-1; i++)
        {
            if(i >= 50)
                sufficient++;
        }

        //If more than 2 stats are sufficient, keep moving
        if(sufficient > 2)
            return eye.easiestPath();

        //If stats are not sufficient, then find paths in this order
        Path food = eye.closestFood();
        Path water = eye.closestWater();
        Path trader = eye.closestTrader();
        Path gold = eye.closestGold();

        Path[] paths = {food, water, trader, gold};
        for(int i = 0; i < paths.length; i++)
        {
            if(paths[i] != null)
                return paths[i];
        }

        //No collectables? Keep moving
        return eye.easiestPath();
    }
}

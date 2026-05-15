package com.wss.player;

import com.wss.player.vision.Vision;
import com.wss.spacial.Path;

public class AdventureBrain extends Brain {

    public AdventureBrain()
    {

    }

    @Override
    public Path makeMove(Vision eye, int[] stats)
    {
        if(stats[0] < 15)
        {
            return null;
        }

        if(stats[1] < 25)
        {
            Path water = eye.closestWater();

            if(water != null)
            {
                return water;
            }
        }

        if(stats[2] < 25)
        {
            Path food = eye.closestFood();

            if(food != null)
            {
                return food;
            }
        }

        Path gold = eye.closestGold();

        if(gold != null && stats[3] < 5)
        {
            return gold;
        }

        return eye.easiestPath();
    }
}
package com.wss.player;

import com.wss.player.vision.Vision;
import com.wss.spacial.Path;


public class GreedyBrain extends Brain{
    public GreedyBrain()
    {}

    //prioritizes the big bucks (gold)
    public Path makeMove(Vision eye, int[] stats)
    {
        Path gold1 = eye.closestGold();
        Path gold2 = eye.secondClosestGold();

        if(gold1 != null)
            return gold1;
        else if (gold2 != null)
            return gold2;
        else
        {
            Path trader1 = eye.closestTrader();
            Path trader2 = eye.secondClosestTrader();

            if (trader1 != null)
                return trader1;
            else if (trader2 != null)
                return trader2;

            //No available traders if it reaches to this point

            //If strength is below 25, rest
            if(stats[0] < 25)
                return null;
            
            //Get easiest path if all conditions fail
            return eye.easiestPath();
        }
    }
}

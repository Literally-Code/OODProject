package com.wss.player;

//Balanced between all stats
public class StandardPlayer extends Player{
    
    public StandardPlayer()
    {
        maxStrength = 100;
        maxWater = 100;
        maxFood = 100;

        setStrength(maxStrength);
        setWater(maxWater);
        setFood(maxFood);
        setGold(5);
    }
}

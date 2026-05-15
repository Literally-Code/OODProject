package com.wss.player;

//Less strength, more food and water
public class LightweightPlayer extends Player{

    public LightweightPlayer()
    {
        maxStrength = 80;
        maxWater = 120;
        maxFood = 120;

        setStrength(maxStrength);
        setWater(maxWater);
        setFood(maxFood);
        setGold(5);
    }
}

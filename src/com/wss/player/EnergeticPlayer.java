package com.wss.player;

//More strength, less food and water
public class EnergeticPlayer extends Player{
    private int maxStrength, maxWater, maxFood;

    public EnergeticPlayer()
    {
        maxStrength = 120;
        maxWater = 80;
        maxFood = 80;

        setStrength(maxStrength);
        setWater(maxWater);
        setFood(maxFood);
        setGold(5);
    }
}

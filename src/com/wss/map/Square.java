package com.wss.map;

import com.wss.items.Food;
import com.wss.items.Gold;
import com.wss.items.Items;
import com.wss.items.Trader;
import com.wss.items.Water;

record TerrainData(double energyCost, double foodCost, double waterCos, char renderChar) {}

enum TerrainType {
    GRASS(new TerrainData(1.5, 1.5, 1, '"')), 
    SAND(new TerrainData(1, 1, 2, '#')), 
    WATER(new TerrainData(3, 3, -1, '~'));

    private final TerrainData data;

    TerrainType(TerrainData data)
    {
        this.data = data;
    }

    public TerrainData getData() {
        return data;
    }
}

public class Square {
    private Items[] items;
    private TerrainType type;

    public Square()
    {
        this.type = TerrainType.GRASS;
    }

    public Square(TerrainType type)
    {
        this.type = type;
    }

    public Square(Items[] items, TerrainType type)
    {
        this.items = items.clone();
        this.type = TerrainType.GRASS;
    }

    public boolean hasItems()
    {
        return this.items.length > 0;
    }

    public boolean hasFood()
    {
        if (!hasItems())
        {
            return false;
        }

        for (Items item : items) {
            if (item instanceof Food) {
                return true;
            }
        }

        return false;
    }

    public boolean hasWater()
    {
        if (!hasItems())
        {
            return false;
        }

        for (Items item : items) {
            if (item instanceof Water) {
                return true;
            }
        }

        return false;
    }

    public boolean hasGold()
    {
        if (!hasItems())
        {
            return false;
        }

        for (Items item : items) {
            if (item instanceof Gold) {
                return true;
            }
        }

        return false;
    }

    public boolean hasTrader()
    {
        if (!hasItems())
        {
            return false;
        }

        for (Items item : items) {
            if (item instanceof Trader) {
                return true;
            }
        }

        return false;
    }

    public char getRenderChar()
    {
        return this.type.getData().renderChar();
    }
}

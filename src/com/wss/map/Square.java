package com.wss.map;

import com.wss.items.Food;
import com.wss.items.Gold;
import com.wss.items.Items;
import com.wss.items.Trader;
import com.wss.items.Water;
import com.wss.spacial.Position;
import java.util.Arrays;
import java.util.Random;

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

    public TerrainData getData()
    {
        return data;
    }
}

public class Square {
    private Items[] items;
    private TerrainType type;
    private String[] localMap;
    private boolean itemsChanged;
    private MapGrid map;
    private Position pos;

    public Square(MapGrid map, Position pos)
    {
        this.type = TerrainType.GRASS;
        this.map = map;
        int res = map.getResolution();
        this.localMap = new String[res * res];
        Arrays.fill(this.localMap, Character.toString(this.getRenderChar()));
        this.itemsChanged = true;
        this.items = new Items[0];
        this.pos = pos.clone();
    }

    public Square(MapGrid map, Position pos, TerrainType type)
    {
        this(map, pos);
        this.type = type;
        Arrays.fill(this.localMap, Character.toString(this.getRenderChar()));
    }

    public Square(MapGrid map, Position pos, TerrainType type, Items[] items)
    {
        this(map, pos, type);
        this.items = items.clone();
        this.itemsChanged = true;
        this.type = type;
    }

    public void addItem(Items item)
    {
        Items[] newItems = new Items[this.items.length + 1];

        for (int i = 0; i < this.items.length; i++)
        {
            newItems[i] = this.items[i];
        }

        newItems[newItems.length - 1] = item;
        this.items = newItems;
        this.itemsChanged = true;
    }

    public Items[] collectItems()
    {
        Items[] collected = this.items;
        this.items = new Items[0];
        this.itemsChanged = true;

        return collected;
    }

    public static void updateTile(Square tile)
    {
        if (tile.itemsChanged)
        {
            Arrays.fill(tile.localMap, Character.toString(tile.getRenderChar()));

            if (tile.hasItems())
            {
                for (int i = 0; i < tile.items.length && i < tile.localMap.length - 1; i++)
                {
                    Items item = tile.items[i];

                    Random random = new Random();
                    int randIndex = random.nextInt(tile.localMap.length);

                    while (!tile.localMap[randIndex].equals(Character.toString(tile.getRenderChar())))
                    {
                        randIndex = randIndex < tile.localMap.length - 1 ? randIndex + 1 : 0;
                    }

                    tile.localMap[randIndex] = item.getSprite();
                }
            }

            tile.itemsChanged = false;
        }
    }

    public double getMovementCost()
    {
        return this.type.getData().energyCost();
    }

    public double distanceFrom(Position from)
    {
        return this.pos.distanceFrom(from);
    }

    public static void renderTile(Square tile, int row, int col)
    {
        int res = tile.map.getResolution();
        int transformedRow = res * row;
        int transformedCol = res * col;

        for (int w = 0; w < res; w++)
        {
            System.out.printf("\u001b[%d;%dH", transformedRow + w + 1, transformedCol + 1);

            for (int h = 0; h < res; h++)
            {
                System.out.print(tile.localMap[res * w + h]);
            }

            System.out.printf("\u001b[%d;1H", transformedRow + res + 1);
        }
    }

    public boolean hasItems()
    {
        return this.items.length > 0;
    }

    public boolean hasFood()
    {
        for (Items item : items)
        {
            if (item instanceof Food) return true;
        }

        return false;
    }

    public boolean hasWater()
    {
        for (Items item : items)
        {
            if (item instanceof Water) return true;
        }

        return false;
    }

    public boolean hasGold()
    {
        for (Items item : items)
        {
            if (item instanceof Gold) return true;
        }

        return false;
    }

    public boolean hasTrader()
    {
        for (Items item : items)
        {
            if (item instanceof Trader) return true;
        }

        return false;
    }

    public char getRenderChar()
    {
        return this.type.getData().renderChar();
    }

    public Position getPosition()
    {
        return this.pos;
    }
    
    public int getStrengthCost()
    {
        return (int)Math.ceil(this.type.getData().energyCost());
    }

    public int getFoodCost()
    {
        return (int)Math.ceil(this.type.getData().foodCost());
    }

    public int getWaterCost()
    {
        return (int)Math.ceil(this.type.getData().waterCos());
    }

    public void printItems()
    {
        System.out.print("[ ");

        for (Items item : items)
        {
            System.out.print(item.getClass().getSimpleName() + " ");
        }

        System.out.println("]");
    }
}
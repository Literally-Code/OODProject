package com.wss.map;

import java.util.Random;

import com.wss.Difficulty;
import com.wss.items.Food;
import com.wss.items.Gold;
import com.wss.items.Trader;
import com.wss.items.Water;
import com.wss.spacial.Size;

public class MapGrid {
    private Square[] tiles;
    private Size size;
    private double seed;

    public MapGrid(Size size, double seed)
    {
        this.size = (Size)size.clone();
        this.tiles = new Square[size.getWidth() * size.getHeight()];
        this.seed = seed + Math.random(); // Add random value between 0..1 to avoid noise zeroes
    }

    public Size getSize()
    {
        return this.size;
    }

    public void setSize(int width, int height)
    {
        this.size = new Size(width, height);
    }

    public void genTerrain(Difficulty difficulty)
    {
        int rowSize = this.size.getWidth();
        int colSize = this.size.getHeight();
        double difficultyBias;
        double noiseResult;

        if (difficulty == Difficulty.Easy)
        {
            difficultyBias = -0.15;
        }
        else if (difficulty == Difficulty.Medium)
        {
            difficultyBias = 0.1;
        }
        else
        {
            difficultyBias = 0.5;
        }

        for (int h = 0; h < colSize; h++)
        {
            for (int w = 0; w < rowSize; w++)
            {
                noiseResult = 1 + ImprovedNoise.noise(w + this.seed, h + this.seed, 2.0);
                
                if (noiseResult < 0.7 + difficultyBias)
                {
                    this.tiles[w + h * rowSize] = new Square(TerrainType.WATER);
                }
                else if (noiseResult < 1 + difficultyBias)
                {
                    this.tiles[w + h * rowSize] = new Square(TerrainType.SAND);
                }
                else
                {
                    this.tiles[w + h * rowSize] = new Square(TerrainType.GRASS);
                }
            }
        }
    }

    public void genItems()
    {
        Random rand = new Random();

        int rowSize = this.size.getWidth();
        int colSize = this.size.getHeight();

        for (int h = 0; h < colSize; h++)
        {
            for (int w = 0; w < rowSize; w++)
            {
                Square tile = this.tiles[w + h * rowSize];

                double chance = rand.nextDouble();

                if (chance < 0.05)
                {
                    tile.addItem(new Gold());
                }
                else if (chance < 0.10)
                {
                    tile.addItem(new Food(10, true));
                }
                else if (chance < 0.15)
                {
                    tile.addItem(new Water(10, true));
                }
                else if (chance < 0.17)
                {
                    tile.addItem(new Trader());
                }
            }
        }
    }

    public void renderTerrain()
    {
        int rowSize = this.size.getWidth();
        int colSize = this.size.getHeight();

        for (int h = 0; h < colSize; h++)
        {
            for (int w = 0; w < rowSize; w++)
            {
                Square.renderTile(this.tiles[w + h * rowSize], 3, w, h);
            }
        }
    }

    public void debugPrintItems()
    {
    int rowSize = this.size.getWidth();
    int colSize = this.size.getHeight();

    for (int h = 0; h < colSize; h++)
        {
        for (int w = 0; w < rowSize; w++)
            {
            Square tile = this.tiles[w + h * rowSize];

            System.out.print("(" + w + "," + h + ") ");
            tile.printItems();
            }
     }
    }
}

package com.wss;

class Size {
    private int width, height;

    public Size(int width, int height)
    {
        this.width = width;
        this.height = height;
    }

    public void setSize(int width, int height)
    {
        this.width = width;
        this.height = height;
    }

    public void addSize(int width, int height)
    {
        this.width += width;
        this.height += height;
    }

    public int getWidth()
    {
        return this.width;
    }

    public int getHeight()
    {
        return this.height;
    }

    public Size clone()
    {
        return new Size(this.width, this.height);
    }
}

public class Map {
    private Terrain[] tiles;
    private Size size;
    private double seed;

    public Map(Size size, double seed)
    {
        this.size = (Size)size.clone();
        this.tiles = new Terrain[size.getWidth() * size.getHeight()];
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
                    this.tiles[w + h * rowSize] = new Terrain(TerrainType.WATER);
                }
                else if (noiseResult < 1 + difficultyBias)
                {
                    this.tiles[w + h * rowSize] = new Terrain(TerrainType.SAND);
                }
                else
                {
                    this.tiles[w + h * rowSize] = new Terrain(TerrainType.GRASS);
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
                Terrain.renderTile(this.tiles[w + h * rowSize], 3, w, h);
            }
        }
    }
}

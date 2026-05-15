package com.wss.map;

import com.wss.Difficulty;
import com.wss.items.Food;
import com.wss.items.Gold;
import com.wss.items.Trader;
import com.wss.items.Water;
import com.wss.spacial.Position;
import com.wss.spacial.Size;
import java.util.Random;

public class MapGrid {

    private Square[] tiles;

    private Size size;

    private double seed;

    private int resolution;

    private Difficulty difficulty;

    public MapGrid(Size size, double seed, int resolution)
    {
        this.size = (Size)size.clone();

        this.tiles =
            new Square[
                size.getWidth() * size.getHeight()
            ];

        this.seed =
            seed + Math.random();

        this.resolution = resolution;
    }

    public int getResolution()
    {
        return this.resolution;
    }

    public Size getSize()
    {
        return this.size;
    }

    public boolean isValid(Position pos)
    {
        return (pos.row <= this.size.getHeight()) && (pos.col < this.size.getWidth());
    }

    public void setSize(int width, int height)
    {
        this.size = new Size(width, height);
    }

    public Square getSquare(Position position)
    {
        int width = this.size.getWidth();

        return this.tiles[
            position.getCol() +
            position.getRow() * width
        ];
    }

    public void genTerrain(Difficulty difficulty)
    {
        this.difficulty = difficulty;

        int width = this.size.getWidth();

        int height = this.size.getHeight();

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

        for (int row = 0; row < height; row++)
        {
            for (int col = 0; col < width; col++)
            {
                Position tilePos = new Position(row, col);

                noiseResult =
                    1 + ImprovedNoise.noise(
                        col + this.seed,
                        row + this.seed,
                        2.0
                    );

                if (noiseResult < 0.7 + difficultyBias)
                {
                    this.tiles[col + row * width] =
                        new Square(
                            this,
                            tilePos,
                            TerrainType.WATER
                        );
                }
                else if (noiseResult < 1 + difficultyBias)
                {
                    this.tiles[col + row * width] =
                        new Square(
                            this,
                            tilePos,
                            TerrainType.SAND
                        );
                }
                else
                {
                    this.tiles[col + row * width] =
                        new Square(
                            this,
                            tilePos,
                            TerrainType.GRASS
                        );
                }
            }
        }

        /*
         * Prevent maps from generating
         * with only one terrain type.
         */

        ensureTerrainVariety();
    }

    /*
     * Guarantees some grass and sand
     * tiles exist on every map.
     */

    private void ensureTerrainVariety()
    {
        int grassCount = 0;

        int sandCount = 0;

        for(int i = 0; i < tiles.length; i++)
        {
            char terrain =
                tiles[i].getRenderChar();

            if(terrain == '"')
            {
                grassCount++;
            }
            else if(terrain == '#')
            {
                sandCount++;
            }
        }

        int minimumGrass =
            Math.max(3, tiles.length / 5);

        int minimumSand =
            Math.max(3, tiles.length / 6);

        Random rand =
            new Random();

        while(grassCount < minimumGrass)
        {
            int index =
                rand.nextInt(tiles.length);

            Position oldPos = tiles[index].getPosition();

            tiles[index] =
                new Square(
                    this,
                    oldPos,
                    TerrainType.GRASS
                );

            grassCount++;
        }

        while(sandCount < minimumSand)
        {
            int index =
                rand.nextInt(tiles.length);

            Position oldPos = tiles[index].getPosition();

            tiles[index] =
                new Square(
                    this,
                    oldPos,
                    TerrainType.SAND
                );

            sandCount++;
        }
    }

    public void updateTerrain()
    {
        int width = this.size.getWidth();

        int height = this.size.getHeight();

        for (int row = 0; row < height; row++)
        {
            for (int col = 0; col < width; col++)
            {
                Square tile =
                    this.tiles[col + row * width];

                Square.updateTile(tile);
            }
        }
    }

    public void genItems()
    {
        Random rand = new Random();

        int width = this.size.getWidth();

        int height = this.size.getHeight();

        double traderChance;
        double goldChance;
        double waterChance;
        double foodChance;

        if(this.difficulty == Difficulty.Easy)
        {
            traderChance = 0.06;
            goldChance = 0.25;
            waterChance = 0.40;
            foodChance = 0.55;
        }
        else if(this.difficulty == Difficulty.Medium)
        {
            traderChance = 0.04;
            goldChance = 0.18;
            waterChance = 0.28;
            foodChance = 0.38;
        }
        else
        {
            traderChance = 0.02;
            goldChance = 0.10;
            waterChance = 0.16;
            foodChance = 0.22;
        }

        for (int row = 0; row < height; row++)
        {
            for (int col = 0; col < width; col++)
            {
                Square tile =
                    this.tiles[col + row * width];

                boolean hasTrader = false;

                for (
                    int i = 0;
                    i < this.resolution && !hasTrader;
                    i++
                )
                {
                    double chance =
                        rand.nextDouble();

                    if (chance < traderChance)
                    {
                        tile.addItem(
                            new Trader()
                        );

                        hasTrader = true;
                    }
                    else if (chance < goldChance)
                    {
                        tile.addItem(
                            new Gold()
                        );
                    }
                    else if (chance < waterChance)
                    {
                        tile.addItem(
                            new Water(5, true)
                        );
                    }
                    else if (chance < foodChance)
                    {
                        tile.addItem(
                            new Food(5, true)
                        );
                    }
                }
            }
        }
    }

    public void renderTerrain(Position playerPos)
    {
        int width = this.size.getWidth();

        int height = this.size.getHeight();

        System.out.println("MAP");

        for (int row = 0; row < height; row++)
        {
            for (int col = 0; col < width; col++)
            {
                if (
                    playerPos != null &&
                    playerPos.getRow() == row &&
                    playerPos.getCol() == col
                )
                {
                    System.out.print("P ");
                }
                else
                {
                    Square tile =
                        this.tiles[
                            col + row * width
                        ];

                    System.out.print(
                        tile.getRenderChar() + " "
                    );
                }
            }

            System.out.println();
        }
    }

    public void debugPrintItems()
    {
        int width = this.size.getWidth();

        int height = this.size.getHeight();

        System.out.println("\nITEMS");

        for (int row = 0; row < height; row++)
        {
            for (int col = 0; col < width; col++)
            {
                Square tile =
                    this.tiles[col + row * width];

                System.out.print(
                    "(" + row + "," + col + ") "
                );

                tile.printItems();
            }
        }

        System.out.println();
    }
}
package com.wss;

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

public class Terrain {
    private Items[] items;
    private TerrainType type;

    public Terrain()
    {
        this.type = TerrainType.GRASS;
    }

    public Terrain(TerrainType type)
    {
        this.type = type;
    }

    public Terrain(Items[] items, TerrainType type)
    {
        this.items = items.clone();
        this.type = TerrainType.GRASS;
    }

    public boolean hasItems()
    {
        return this.items.length > 0;
    }

    public char getRenderChar()
    {
        return this.type.getData().renderChar();
    }
}

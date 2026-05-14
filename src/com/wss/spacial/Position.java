package com.wss.spacial;

public class Position {
    private int row;
    private int col;
    
    public Position(int row, int col)
    {
        this.row = row;
        this.col = col;
    }

    public int[] getPosition()
    {
        int[] pos = {row, col};
        return pos;
    }
}

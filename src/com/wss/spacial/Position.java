package com.wss.spacial;

public class Position {

    public final int row;
    public final int col;

    public Position(int row, int col) {

        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int[] getPosition() {

        int[] pos = {row, col};
        return pos;
    }

    public double distanceFrom(Position from)
    {
        return Math.sqrt(Math.pow((this.row - from.row), 2) + Math.pow(this.col - from.col, 2));
    }

    public Position clone()
    {
        return new Position(row, col);
    }

    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }
}

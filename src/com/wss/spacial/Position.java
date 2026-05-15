package com.wss.spacial;

public class Position {

    private final int row;
    private final int col;

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

    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }
}
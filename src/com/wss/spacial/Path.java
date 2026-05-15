package com.wss.spacial;

import java.util.ArrayList;
import java.util.List;

public class Path {

    private List<Position> moves;

    public Path() {
        moves = new ArrayList<>();
    }

    public void addMove(Position pos) {
        moves.add(pos);
    }

    public List<Position> getMoves() {
        return moves;
    }

    public boolean isEmpty() {
        return moves.isEmpty();
    }

    @Override
    public String toString() {
        return moves.toString();
    }
}
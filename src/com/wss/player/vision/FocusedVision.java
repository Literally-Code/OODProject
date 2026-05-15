package com.wss.player.vision;

import java.util.*;

import com.wss.map.MapGrid;
import com.wss.map.Square;
import com.wss.spacial.Position;

/**
 * Sees only one square directly to the east.
 */
public class FocusedVision extends Vision {

    public FocusedVision(MapGrid map, Position pos) {
        super(map, pos);
    }

    @Override
    public List<Square> getVisibleSquares() {
        List<Square> visible = new ArrayList<>();

        int[] tempPos = playerPos.getPosition();
        Position east = new Position(tempPos[0], tempPos[1] + 1);

        if (map.isValid(east)) {
            visible.add(map.getSquare(east));
        }

        return visible;
    }
}
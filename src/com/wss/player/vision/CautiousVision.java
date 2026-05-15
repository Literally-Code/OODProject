package com.wss.player.vision;

import java.util.*;
import com.wss.map.MapGrid;
import com.wss.map.Square;
import com.wss.spacial.Position;

/**
 * Sees north, south, and east of the player.
 */
public class CautiousVision extends Vision {

    public CautiousVision(MapGrid map, Position pos) {
        super(map, pos);
    }

    @Override
    public List<Square> getVisibleSquares() {
        List<Square> visible = new ArrayList<>();

        int[][] dirs = {
                {-1, 0}, // north
                {1, 0},  // south
                {0, 1}   // east
        };

        for (int[] d : dirs) {
            int[] tempPos = playerPos.getPosition();
            Position newPos = new Position(tempPos[0] + d[0], tempPos[1] + d[1]);

            if (map.isValid(newPos)) {
                visible.add(map.getSquare(newPos));
            }
        }

        return visible;
    }
}
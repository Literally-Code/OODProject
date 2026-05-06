package com.wss.player.vision;

import java.util.*;

import com.wss.map.MapGrid;
import com.wss.map.Square;
import com.wss.spacial.Position;

/**
 * Sees a wider area around and ahead of the player.
 */
public class FarSightVision extends Vision {

    public FarSightVision(MapGrid map, Position pos) {
        super(map, pos);
    }

    @Override
    public List<Square> getVisibleSquares() {
        List<Square> visible = new ArrayList<>();

        int[][] dirs = {
                {-2, 0}, {-2, 1},
                {-1, 0}, {-1, 1}, {-1, 2},
                {0, 1},  {0, 2},
                {1, 0},  {1, 1},  {1, 2},
                {2, 0},  {2, 1}
        };

        for (int[] d : dirs) {
            Position newPos = new Position(playerPos.row + d[0], playerPos.col + d[1]);

            if (map.isValid(newPos)) {
                visible.add(map.getSquare(newPos));
            }
        }

        return visible;
    }
}
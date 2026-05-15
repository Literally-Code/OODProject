package com.wss.player.vision;

import com.wss.map.MapGrid;
import com.wss.spacial.Position;
import java.util.ArrayList;
import java.util.List;

public class FarSightVision extends Vision {

    public FarSightVision(MapGrid map, Position pos) {
        super(map, pos);
    }

    @Override
    protected List<Position> getVisiblePositions() {

        List<Position> visible = new ArrayList<>();

        int[][] offsets = {
            {-2, 0}, {-2, 1},
            {-1, 0}, {-1, 1}, {-1, 2},
            {0, 1}, {0, 2},
            {1, 0}, {1, 1}, {1, 2},
            {2, 0}, {2, 1}
        };

        for (int[] offset : offsets) {

            visible.add(
                (Position) offsetFromPlayer(offset[0], offset[1])
            );
        }

        return visible;
    }
}
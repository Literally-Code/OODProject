package com.wss.player.vision;

import com.wss.map.MapGrid;
import com.wss.spacial.Position;
import java.util.ArrayList;
import java.util.List;

public class FocusedVision extends Vision {

    public FocusedVision(MapGrid map, Position pos) {
        super(map, pos);
    }

    @Override
    protected List<Position> getVisiblePositions() {

        List<Position> visible = new ArrayList<>();

        visible.add((Position) offsetFromPlayer(0, 1));

        return visible;
    }
}
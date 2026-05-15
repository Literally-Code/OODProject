package com.wss.player.vision;

import com.wss.map.MapGrid;
import com.wss.spacial.Position;
import java.util.ArrayList;
import java.util.List;

public class KeenEyedVision extends Vision {

    public KeenEyedVision(MapGrid map, Position pos) {
        super(map, pos);
    }

    @Override
    protected List<Position> getVisiblePositions() {

        List<Position> visible = new ArrayList<>();

        visible.add((Position) offsetFromPlayer(-1, 0));
        visible.add((Position) offsetFromPlayer(-1, 1));
        visible.add((Position) offsetFromPlayer(0, 1));
        visible.add((Position) offsetFromPlayer(1, 1));
        visible.add((Position) offsetFromPlayer(1, 0));
        visible.add((Position) offsetFromPlayer(0, 2));

        return visible;
    }
}
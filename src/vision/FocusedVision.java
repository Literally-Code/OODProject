import java.util.*;

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

        Position east = new Position(playerPos.row, playerPos.col + 1);

        if (map.isValid(east)) {
            visible.add(map.getSquare(east));
        }

        return visible;
    }
}
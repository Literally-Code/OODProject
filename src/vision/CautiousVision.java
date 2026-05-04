import java.util.*;

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
            Position newPos = new Position(playerPos.row + d[0], playerPos.col + d[1]);

            if (map.isValid(newPos)) {
                visible.add(map.getSquare(newPos));
            }
        }

        return visible;
    }
}
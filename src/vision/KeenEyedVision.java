import java.util.*;

/**
 * Sees surrounding squares plus extended vision to the east.
 */
public class KeenEyedVision extends Vision {

    public KeenEyedVision(MapGrid map, Position pos) {
        super(map, pos);
    }

    @Override
    public List<Square> getVisibleSquares() {
        List<Square> visible = new ArrayList<>();

        int[][] dirs = {
                {-1, 0}, {-1, 1},
                {0, 1},  {1, 1},
                {1, 0},
                {0, 2} // extra east
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
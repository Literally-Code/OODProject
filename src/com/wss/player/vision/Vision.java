package com.wss.player.vision;

import com.wss.map.MapGrid;
import com.wss.map.Square;
import com.wss.spacial.Path;
import com.wss.spacial.Position;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public abstract class Vision {

    protected MapGrid map;

    protected Position playerPos;

    public Vision(MapGrid map, Position playerPos)
    {
        this.map = map;

        this.playerPos = playerPos;
    }

    public void setPlayerPosition(Position position)
    {
        this.playerPos = position;
    }

    public Path closestFood()
    {
        return findBestPath(square -> square.hasFood(), 0);
    }

    public Path closestWater()
    {
        return findBestPath(square -> square.hasWater(), 0);
    }

    public Path closestGold()
    {
        return findBestPath(square -> square.hasGold(), 0);
    }

    public Path closestTrader()
    {
        return findBestPath(square -> square.hasTrader(), 0);
    }

    public Path secondClosestFood()
    {
        return findBestPath(square -> square.hasFood(), 1);
    }

    public Path secondClosestWater()
    {
        return findBestPath(square -> square.hasWater(), 1);
    }

    public Path secondClosestGold()
    {
        return findBestPath(square -> square.hasGold(), 1);
    }

    public Path secondClosestTrader()
    {
        return findBestPath(square -> square.hasTrader(), 1);
    }

    public Path easiestPath()
    {
        List<VisibleSquare> candidates =
            getVisibleSquareData();

        if(candidates.isEmpty())
        {
            return null;
        }

        candidates.sort(
            Comparator
                .comparingInt(
                    (VisibleSquare data) ->
                        getMovementCost(data.square)
                )
                .thenComparingInt(
                    data ->
                        distanceBetween(
                            playerPos,
                            data.position
                        )
                )
        );

        int randomIndex =
            (int)(Math.random() * candidates.size());

        return buildPath(
            playerPos,
            candidates.get(randomIndex).position
        );
    }

    public List<Square> getVisibleSquares()
    {
        List<Square> visible =
            new ArrayList<>();

        for(VisibleSquare data : getVisibleSquareData())
        {
            visible.add(data.square);
        }

        return visible;
    }

    protected abstract List<Position> getVisiblePositions();

    protected Position offsetFromPlayer(
        int rowOffset,
        int colOffset
    )
    {
        int[] current =
            playerPos.getPosition();

        return new Position(
            current[0] + rowOffset,
            current[1] + colOffset
        );
    }

    private Path findBestPath(
        Predicate<Square> filter,
        int targetIndex
    )
    {
        List<VisibleSquare> candidates =
            new ArrayList<>();

        for(VisibleSquare data : getVisibleSquareData())
        {
            if(filter.test(data.square))
            {
                candidates.add(data);
            }
        }

        if(candidates.size() <= targetIndex)
        {
            return null;
        }

        candidates.sort(
            Comparator
                .comparingInt(
                    (VisibleSquare data) ->
                        distanceBetween(
                            playerPos,
                            data.position
                        )
                )
                .thenComparingInt(
                    data ->
                        getMovementCost(data.square)
                )
        );

        return buildPath(
            playerPos,
            candidates.get(targetIndex).position
        );
    }

    private List<VisibleSquare> getVisibleSquareData()
    {
        List<VisibleSquare> visible =
            new ArrayList<>();

        for(Position position : getVisiblePositions())
        {
            if(isValid(position))
            {
                visible.add(
                    new VisibleSquare(
                        position,
                        getSquare(position)
                    )
                );
            }
        }

        return visible;
    }

    private boolean isValid(Position position)
    {
        int[] coordinates =
            position.getPosition();

        return coordinates[0] >= 0 &&
               coordinates[0] < map.getSize().getHeight() &&
               coordinates[1] >= 0 &&
               coordinates[1] < map.getSize().getWidth();
    }

    private Square getSquare(Position position)
    {
        try
        {
            Field tilesField =
                MapGrid.class.getDeclaredField("tiles");

            tilesField.setAccessible(true);

            Square[] tiles =
                (Square[]) tilesField.get(map);

            int[] coordinates =
                position.getPosition();

            int index =
                coordinates[1] +
                coordinates[0] *
                map.getSize().getWidth();

            return tiles[index];
        }
        catch(ReflectiveOperationException exception)
        {
            throw new IllegalStateException(
                "Vision could not read map tiles.",
                exception
            );
        }
    }

    private int getMovementCost(Square square)
    {
        char terrain =
            square.getRenderChar();

        if(terrain == '~')
        {
            return 3;
        }

        if(terrain == '#')
        {
            return 2;
        }

        return 1;
    }

    private int distanceBetween(
        Position first,
        Position second
    )
    {
        int[] a =
            first.getPosition();

        int[] b =
            second.getPosition();

        return Math.abs(a[0] - b[0]) +
               Math.abs(a[1] - b[1]);
    }

    /*
     * Cardinal movement only:
     *
     * Up
     * Down
     * Left
     * Right
     *
     * No diagonal traversal.
     */

    private Path buildPath(
        Position start,
        Position end
    )
    {
        Path path = new Path();

        Position current = start;

        while(!samePosition(current, end))
        {
            int[] currentCoordinates =
                current.getPosition();

            int[] endCoordinates =
                end.getPosition();

            int currentRow =
                currentCoordinates[0];

            int currentCol =
                currentCoordinates[1];

            int endRow =
                endCoordinates[0];

            int endCol =
                endCoordinates[1];

            Position next;

            if(currentRow < endRow)
            {
                next =
                    new Position(
                        currentRow + 1,
                        currentCol
                    );
            }
            else if(currentRow > endRow)
            {
                next =
                    new Position(
                        currentRow - 1,
                        currentCol
                    );
            }
            else if(currentCol < endCol)
            {
                next =
                    new Position(
                        currentRow,
                        currentCol + 1
                    );
            }
            else
            {
                next =
                    new Position(
                        currentRow,
                        currentCol - 1
                    );
            }

            path.addMove(next);

            current = next;
        }

        return path;
    }

    private boolean samePosition(
        Position first,
        Position second
    )
    {
        int[] a =
            first.getPosition();

        int[] b =
            second.getPosition();

        return a[0] == b[0] &&
               a[1] == b[1];
    }

    private static class VisibleSquare {

        private final Position position;

        private final Square square;

        private VisibleSquare(
            Position position,
            Square square
        )
        {
            this.position = position;

            this.square = square;
        }
    }
}
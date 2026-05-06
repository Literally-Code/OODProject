package com.wss.player.vision;

import java.util.*;

import com.wss.spacial.Position;
import com.wss.spacial.Path;
import com.wss.map.Square;
import com.wss.map.MapGrid;

/**
 * Evaluates nearby squares and generates optimal paths to targets.
 * Subclasses define visibility scope.
 */
public abstract class Vision {

    protected MapGrid map;
    protected Position playerPos;

    public Vision(MapGrid map, Position playerPos) {
        this.map = map;
        this.playerPos = playerPos;
    }

    public Path closestFood() {
        return findBestPath(s -> s.hasFood());
    }

    public Path closestWater() {
        return findBestPath(s -> s.hasWater());
    }

    public Path closestGold() {
        return findBestPath(s -> s.hasGold());
    }

    public Path closestTrader() {
        return findBestPath(s -> s.hasTrader());
    }

    public Path easiestPath() {
        return getVisibleSquares().stream()
                .min(Comparator.comparingInt(Square::getMovementCost))
                .map(s -> buildPath(playerPos, s.getPosition()))
                .orElse(new Path());
    }

    public Path secondClosestFood() {
        return findSecondBest(s -> s.hasFood());
    }

    public Path secondClosestWater() {
        return findSecondBest(s -> s.hasWater());
    }

    public Path secondClosestGold() {
        return findSecondBest(s -> s.hasGold());
    }

    public Path secondClosestTrader() {
        return findSecondBest(s -> s.hasTrader());
    }

    public abstract List<Square> getVisibleSquares();

    /**
     * Ranking priority:
     * 1. Shortest distance
     * 2. Lowest movement cost
     * 3. Furthest east
     */
    private Path findBestPath(java.util.function.Predicate<Square> filter) {
        List<Square> candidates = getVisibleSquares().stream()
                .filter(filter)
                .toList();

        if (candidates.isEmpty()) return new Path();

        candidates.sort(Comparator
                .comparingInt(s -> s.distanceFrom(playerPos))
                .thenComparingInt(Square::getMovementCost)
                .thenComparing((Square s) -> -s.getPosition().col)
        );

        return buildPath(playerPos, candidates.get(0).getPosition());
    }

    private Path findSecondBest(java.util.function.Predicate<Square> filter) {
        List<Square> candidates = getVisibleSquares().stream()
                .filter(filter)
                .toList();

        if (candidates.size() < 2) return new Path();

        candidates.sort(Comparator
                .comparingInt(s -> s.distanceFrom(playerPos))
                .thenComparingInt(Square::getMovementCost)
                .thenComparing((Square s) -> -s.getPosition().col)
        );

        return buildPath(playerPos, candidates.get(1).getPosition());
    }

    private Path buildPath(Position start, Position end) {
        Path path = new Path();
        Position current = start;

        while (!current.equals(end)) {
            int dx = Integer.compare(end.row, current.row);
            int dy = Integer.compare(end.col, current.col);

            Position next = new Position(current.row + dx, current.col + dy);
            path.addMove(next);
            current = next;
        }

        return path;
    }
}
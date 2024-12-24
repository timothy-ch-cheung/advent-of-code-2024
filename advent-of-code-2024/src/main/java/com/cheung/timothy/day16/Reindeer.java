package com.cheung.timothy.day16;

import com.cheung.timothy.day06.Coord;

import java.util.*;

class Reindeer {
    private Coord currLoc;
    private Direction currDir;
    private int cost;
    private Set<Coord> visited;

    public Reindeer(Coord loc, Direction dir, int cost, Set<Coord> visited) {
        this.currLoc = loc;
        this.currDir = dir;
        this.cost = cost;
        this.visited = visited;
    }

    public Reindeer(Coord loc, Direction dir, int cost) {
        this(loc, dir, cost, new HashSet<>());
    }

    public void moveTo(Coord newLoc) {
        visited.add(newLoc);
        int dx = newLoc.getX() - currLoc.getX();
        int dy = newLoc.getY() - currLoc.getY();
        assert Math.abs(dx) == 1 ^ Math.abs(dy) == 1;
        Direction newDir = null;
        if (dx == 1) {
            newDir = Direction.RIGHT;
        }
        if (dx == -1) {
            newDir = Direction.LEFT;
        }
        if (dy == 1) {
            newDir = Direction.DOWN;
        }
        if (dy == -1) {
            newDir = Direction.UP;
        }
        assert newDir != null;

        if (!newDir.equals(currDir)) {
            currDir = newDir;
            cost += 1000;
        }
        currLoc = newLoc;
        cost += 1;
    }

    public int getCost() {
        return cost;
    }

    public Coord getCurrLoc() {
        return currLoc;
    }

    public boolean alreadyVisited(Coord coord) {
        return visited.contains(coord);
    }

    public List<Coord> next() {
        List<Direction> nextDirections = Arrays.asList(Direction.LEFT, Direction.RIGHT, Direction.UP, Direction.DOWN);
        List<Coord> next = new ArrayList<>();
        for (Direction direction : nextDirections) {
            switch (direction) {
                case RIGHT -> next.add(new Coord(currLoc.getX() + 1, currLoc.getY()));
                case LEFT -> next.add(new Coord(currLoc.getX() - 1, currLoc.getY()));
                case DOWN -> next.add(new Coord(currLoc.getX(), currLoc.getY() + 1));
                case UP -> next.add(new Coord(currLoc.getX(), currLoc.getY() - 1));
            }
        }
        return next;
    }

    public Set<Coord> getVisited() {
        return visited;
    }

    public Reindeer clone() {
        return new Reindeer(currLoc, currDir, cost, new HashSet<>(visited));
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Reindeer reindeer)) return false;
        return cost == reindeer.cost && Objects.equals(currLoc, reindeer.currLoc) && currDir == reindeer.currDir;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currLoc, currDir, cost);
    }
}

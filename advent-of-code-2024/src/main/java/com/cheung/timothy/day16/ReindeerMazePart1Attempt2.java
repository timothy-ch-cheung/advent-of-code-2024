package com.cheung.timothy.day16;

import com.cheung.timothy.day06.Coord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class ReindeerMazePart1Attempt2 {

    private static final char START = 'S';
    private static final char END = 'E';
    private static final char SPACE = '.';

    public static void main(String[] args) throws IOException {

        ClassLoader classLoader = ReindeerMazePart1Attempt2.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream("ReindeerMaze/input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            List<List<Character>> map = new ArrayList<>();
            int rows = 0;
            int columns = 0;
            Coord start = null;
            Coord end = null;

            while ((line = reader.readLine()) != null) {
                List<Character> row = new ArrayList<>();
                columns = line.length();
                for (int i = 0; i < line.length(); i++) {
                    row.add(line.charAt(i));
                    if (line.charAt(i) == START) {
                        start = new Coord(i, rows);
                    }
                    if (line.charAt(i) == END) {
                        end = new Coord(i, rows);
                    }
                }
                map.add(row);
                rows++;
            }
            assert rows > 0;
            assert columns > 0;
            assert start != null;
            assert end != null;

            Map<Coord, Integer> cost = new HashMap<>();
            List<Reindeer> candidates = new ArrayList<>();
            Reindeer initial = new Reindeer(start, Direction.RIGHT, 0);
            candidates.add(initial);
            List<Reindeer> successful = new ArrayList<>();

            while (!candidates.isEmpty()) {
                candidates.sort(Comparator.comparingInt(Reindeer::getCost));
                Reindeer candidate = candidates.get(0);

                List<Coord> next = candidate.next();
                next = next.stream().filter(coord -> isWithinBounds(coord, map) && (map.get(coord.getY()).get(coord.getX()) == SPACE || map.get(coord.getY()).get(coord.getX()) == END)).toList();
                for (int i = 1; i < next.size(); i++) {
                    if (candidate.alreadyVisited(next.get(i))) {
                        continue;
                    }
                    Reindeer reindeer = candidate.clone();
                    reindeer.moveTo(next.get(i));
                    candidates.add(reindeer);
                    Integer knownCost = cost.get(next.get(i));
                    if (knownCost == null) {
                        cost.put(next.get(i), reindeer.getCost());
                    } else if (reindeer.getCost() > knownCost) {
                        candidates.remove(reindeer);
                    }
                }
                if (candidate.alreadyVisited(next.get(0))) {
                    candidates.remove(candidate);
                } else {
                    candidate.moveTo(next.get(0));
                    Integer knownCost = cost.get(next.get(0));
                    if (knownCost == null) {
                        cost.put(next.get(0), candidate.getCost());
                    } else if (candidate.getCost() > knownCost) {
                        candidates.remove(candidate);
                    }
                }
                if (candidate.getCurrLoc().equals(end)) {
                    successful.add(candidate);
                    candidates.remove(candidate);
                }
            }

            System.out.println("Lowest Score: " + successful.get(0).getCost());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isWithinBounds(Coord coord, List<List<Character>> map) {
        return coord.getX() >= 0 && coord.getX() < map.get(0).size() && coord.getY() >= 0 && coord.getY() < map.size();
    }
}

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


enum Direction {
    RIGHT,
    LEFT,
    DOWN,
    UP;
}
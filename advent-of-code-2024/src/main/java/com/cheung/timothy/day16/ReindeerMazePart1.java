package com.cheung.timothy.day16;

import com.cheung.timothy.day06.Coord;
import com.cheung.timothy.day13.ClawContraptionPart1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class ReindeerMazePart1 {

    private static final char START = 'S';
    private static final char END = 'E';
    private static final char SPACE = '.';
    private static final char WALL = '#';

    public static void main(String[] args) throws IOException {

        ClassLoader classLoader = ReindeerMazePart1.class.getClassLoader();

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

            Queue<Coord> queue = new ArrayDeque<>();
            Map<Coord, Set<Coord>> visited = new HashMap<>();

            queue.add(start);
            visited.put(start, new HashSet<>());
            while(queue.peek() != null) {
                Coord curr = queue.remove();

                Coord right = new Coord(curr.getX() + 1, curr.getY());
                Coord left = new Coord(curr.getX() - 1, curr.getY());
                Coord down = new Coord(curr.getX(), curr.getY() + 1);
                Coord up = new Coord(curr.getX(), curr.getY() - 1);

                processNode(map, queue, visited, curr, right);
                processNode(map, queue, visited, curr, left);
                processNode(map, queue, visited, curr, down);
                processNode(map, queue, visited, curr, up);
            }

            List<List<Coord>> routes = new ArrayList<>();
            findAllRoutes(end, start, visited, new ArrayList<>(), routes, new HashSet<>());

            int lowestScore = Integer.MAX_VALUE;

            System.out.println("Lowest Score: " + lowestScore);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processNode(List<List<Character>> map, Queue<Coord> queue, Map<Coord, Set<Coord>> visited, Coord curr, Coord next) {
        if (!isWithinBounds(next, map) || map.get(next.getY()).get(next.getX()) == WALL) {
            return;
        }
        if (!visited.containsKey(next)) {
            Set<Coord> previous = new HashSet<>();
            previous.add(curr);
            queue.add(next);
            visited.put(next, previous);
        } else {
            visited.get(next).add(curr);
        }
    }

    private static void findAllRoutes(Coord curr, Coord target, Map<Coord, Set<Coord>> visited, List<Coord> path, List<List<Coord>> routes, Set<Coord> visitedNodes) {
        if (visitedNodes.contains(curr)) {
            return;
        }
        visitedNodes.add(curr);
        if (target.equals(curr)) {
            List<Coord> route = new ArrayList<>(path);
            route.add(curr);
            Collections.reverse(route);
            routes.add(route);
        }

        for (Coord next: visited.get(curr)) {
            List<Coord> newPath = new ArrayList<>(path);
            newPath.add(next);
            findAllRoutes(next, target, visited, newPath, routes, visitedNodes);
        }
    }

    private static boolean isWithinBounds(Coord coord, List<List<Character>> map) {
        return coord.getX() >= 0 && coord.getX() < map.get(0).size() && coord.getY() >= 0 && coord.getY() < map.size();
    }
}

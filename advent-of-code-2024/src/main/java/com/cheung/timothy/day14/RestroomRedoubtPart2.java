package com.cheung.timothy.day14;

import com.cheung.timothy.day06.Coord;
import com.cheung.timothy.day13.ClawContraptionPart1;
import com.cheung.timothy.day13.Delta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.cheung.timothy.day14.RestroomRedoubtPart2.COLS;
import static com.cheung.timothy.day14.RestroomRedoubtPart2.ROWS;

public class RestroomRedoubtPart2 {

    private static final Pattern ROBOT_LOCATION_REGEX = Pattern.compile("p=(-?\\d+),(-?\\d+) v=(-?\\d+),(-?\\d+)");
    public static final int ROWS = 103;
    public static final int COLS = 101;
    private static final int TIME_ELAPSED = 10000;

    public static void main(String[] args) throws IOException {

        ClassLoader classLoader = ClawContraptionPart1.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream("RestroomRedoubt/input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            List<Robot> robots = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                Matcher robotMatcher = ROBOT_LOCATION_REGEX.matcher(line);
                robotMatcher.find();

                int startX = Integer.parseInt(robotMatcher.group(1));
                int startY = Integer.parseInt(robotMatcher.group(2));
                int dx = Integer.parseInt(robotMatcher.group(3));
                int dy = Integer.parseInt(robotMatcher.group(4));

                robots.add(new Robot(startX, startY, dx, dy));
            }

            for (int i = 0; i < TIME_ELAPSED; i++) {
                Map<Integer, Integer> frequency = new HashMap<>();
                robots.forEach(r -> {
                    if (frequency.containsKey(r.getY())) {
                        frequency.put(r.getY(), frequency.get(r.getY()) + 1);
                    } else {
                        frequency.put(r.getY(), 1);
                    }
                });

                if (frequency.values().stream().anyMatch(freq -> freq > 25)) {
                    int[][] map = new int[ROWS][COLS];
                    robots.forEach(r -> {
                        map[r.getY()][r.getX()] = 1;
                    });
                    System.out.println("Time Elapsed: " + i);
                    for (int y = 0; y < ROWS; y++) {
                        for (int x = 0; x < COLS; x++) {
                            if (map[y][x] != 0) {
                                System.out.print(map[y][x]);
                            } else {
                                System.out.print(".");
                            }
                        }
                        System.out.println();
                    }
                    System.out.println("\n\n\n\n\n\n");
                }
                robots.forEach(Robot::advance);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Robot {
    private Coord coord;
    private Delta move;

    public Robot(int startX, int startY, int dx, int dy) {
        this.coord = new Coord(startX, startY);
        this.move = new Delta(dx, dy);
    }

    public int getX() {
        return coord.getX();
    }

    public int getY() {
        return coord.getY();
    }

    public void advance() {
        int endX = (coord.getX() + move.getX()) % COLS;
        if (endX < 0) {
            endX += COLS;
        }
        int endY = (coord.getY() + move.getY()) % ROWS;
        if (endY < 0) {
            endY += ROWS;
        }
        coord.set(endX, endY);
    }
}

//        .....................................................................................................
//        ......1...................................1111111111111111111111111111111............................
//        ..........................................1.............................1............................
//        ..........................................1.............................1............................
//        ....................................1.....1.............................1............................
//        ..........................................1.............................1............................
//        .............................1............1..............1..............1............................
//        ..........................................1.............111.............1............................
//        1...1.....................................1............11111............1............................
//        ......................1...................1...........1111111...........1............................
//        ..........................................1..........111111111..........1............................
//        ...................1......................1............11111............1.................1..1.......
//        ..........................................1...........1111111...........1............................
//        ............................1.............1..........111111111..........1..................1........1
//        ..........................................1.........11111111111.........1..........1.................
//        ..........................................1........1111111111111........1..............1.............
//        ..........................................1..........111111111..........1............................
//        ..........................................1.........11111111111.........1............................
//        ..........1...............................1........1111111111111........1............................
//        .1...................1......1.......1.....1.......111111111111111.......1............................
//        ..........................................1......11111111111111111......1............................
//        .................................1........1........1111111111111........1............................
//        .......1..................................1.......111111111111111.......1..............1.............
//        ........................1.................1......11111111111111111......1..........................1.
//        ................................1.........1.....1111111111111111111.....1............................
//        .....................1....................1....111111111111111111111....1............................
//        ....................1.....................1.............111.............1............................
//        ..................................1.......1.............111.............1............................
//        ......................................1...1.............111.............1............................
//        ............................1.............1.............................1..........1.................
//        .................................1........1.............................1...........................1
//        ........................................1.1.............................1............1...............
//        ..........................................1.............................1............1...............
//        ......1..................1.1..............1111111111111111111111111111111....................1.......
//        .....................................................................................................
//        ..............................................1......................................................
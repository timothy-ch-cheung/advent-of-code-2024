package com.cheung.timothy.day14;

import com.cheung.timothy.day13.ClawContraptionPart1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RestroomRedoubtPart1 {

    private static final Pattern ROBOT_LOCATION_REGEX = Pattern.compile("p=(-?\\d+),(-?\\d+) v=(-?\\d+),(-?\\d+)");
    private static final int TIME_ELAPSED = 100;
    private static final int ROWS = 103;
    private static final int COLS = 101;

    public static void main(String[] args) throws IOException {

        ClassLoader classLoader = ClawContraptionPart1.class.getClassLoader();

        int[][] map = new int[ROWS][COLS];

        try (InputStream inputStream = classLoader.getResourceAsStream("RestroomRedoubt/input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            while ((line = reader.readLine()) != null) {
                Matcher robotMatcher = ROBOT_LOCATION_REGEX.matcher(line);
                robotMatcher.find();

                int startX = Integer.parseInt(robotMatcher.group(1));
                int startY = Integer.parseInt(robotMatcher.group(2));
                int dx = Integer.parseInt(robotMatcher.group(3)) * TIME_ELAPSED;
                int dy = Integer.parseInt(robotMatcher.group(4)) * TIME_ELAPSED;

                int endX = (startX + dx) % COLS;
                if (endX < 0) {
                    endX += COLS;
                }
                int endY = (startY + dy) % ROWS;
                if (endY < 0) {
                    endY += ROWS;
                }

                map[endY][endX]++;
            }

            int firstQuadrant = 0;
            for (int y = 0; y < ROWS / 2; y++) {
                for (int x = 0; x < COLS / 2; x++) {
                    firstQuadrant += map[y][x];
                }
            }

            int secondQuadrant = 0;
            for (int y = 0; y < ROWS / 2; y++) {
                for (int x = COLS / 2 + 1; x < COLS; x++) {
                    secondQuadrant += map[y][x];
                }
            }

            int thirdQuadrant = 0;
            for (int y = ROWS / 2 + 1; y < ROWS; y++) {
                for (int x = 0; x < COLS / 2; x++) {
                    thirdQuadrant += map[y][x];
                }
            }

            int fourthQuadrant = 0;
            for (int y = ROWS / 2 + 1; y < ROWS; y++) {
                for (int x = COLS / 2 + 1; x < COLS; x++) {
                    fourthQuadrant += map[y][x];
                }
            }

            int safetyFactor = firstQuadrant * secondQuadrant * thirdQuadrant * fourthQuadrant;
            System.out.println("Safety Factor: " + safetyFactor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

package com.cheung.timothy.day11;

import com.cheung.timothy.day10.HoofItPart2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PlutonianPebblesPart1 {

    public static void main(String[] args) throws IOException {

        ClassLoader classLoader = HoofItPart2.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream("PlutonianPebbles/input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line = reader.readLine();

            final int NUM_BLINKS = 25;
            for (int i = 0; i < NUM_BLINKS; i++) {
                line = blink(line);
            }

            System.out.println("Trail Stones: " + line.split(" ").length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String blink(String stones) {
        StringBuilder sb = new StringBuilder();
        String[] stoneList = stones.split(" ");
        for (String stone: stoneList) {
            if ("0".equals(stone)) {
                sb.append("1");
            } else if (stone.length() % 2 == 0) {
                sb.append(Long.parseLong(stone.substring(0, stone.length()/2)));
                sb.append(" ");
                sb.append(Long.parseLong(stone.substring(stone.length()/2)));
            } else {
                sb.append(Long.parseLong(stone) * 2024);
            }
            sb.append(" ");
        }

        return sb.toString().trim();
    }
}

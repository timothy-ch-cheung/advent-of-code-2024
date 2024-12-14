package com.cheung.timothy.day11;

import com.cheung.timothy.day10.HoofItPart2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PlutonianPebblesPart2 {

    public static void main(String[] args) throws IOException {

        ClassLoader classLoader = HoofItPart2.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream("PlutonianPebbles/input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line = reader.readLine();

            final int NUM_BLINKS = 75;
            long totalStones = 0;
            Map<StoneCacheKey, Long> cache = new HashMap<>();
            for (String stone : line.split(" ")) {
                totalStones += blink(stone, NUM_BLINKS, cache);
            }

            System.out.println("Trail Stones: " + totalStones);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static long blink(String stone, int numBlinksLeft, Map<StoneCacheKey, Long> cache) {
        if (numBlinksLeft == 0) {
            return 1;
        }
        Long cachedNumStones = cache.get(new StoneCacheKey(numBlinksLeft, stone));
        if (cachedNumStones != null) {
            return cachedNumStones;
        }

        if ("0".equals(stone)) {
            long numStones = blink("1", numBlinksLeft - 1, cache);
            cache.put(new StoneCacheKey(numBlinksLeft, stone), numStones);
            return numStones;
        } else if (stone.length() % 2 == 0) {
            String stone1 = Long.toString(Long.parseLong(stone.substring(0, stone.length() / 2)));
            String stone2 = Long.toString(Long.parseLong(stone.substring(stone.length() / 2)));
            long numStones1 = blink(stone1, numBlinksLeft - 1, cache);
            cache.put(new StoneCacheKey(numBlinksLeft - 1, stone1), numStones1);
            long numStones2 = blink(stone2, numBlinksLeft - 1, cache);
            cache.put(new StoneCacheKey(numBlinksLeft - 1, stone2), numStones2);

            long numStones = numStones1 + numStones2;
            cache.put(new StoneCacheKey(numBlinksLeft, stone), numStones);
            return numStones;
        }
        long numStones = blink(Long.toString(Long.parseLong(stone) * 2024), numBlinksLeft - 1, cache);
        cache.put(new StoneCacheKey(numBlinksLeft, stone), numStones);
        return numStones;
    }
}

class StoneCacheKey {
    private final int blinks;
    private final String stone;

    public StoneCacheKey(int blinks, String stone) {
        this.blinks = blinks;
        this.stone = stone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StoneCacheKey that)) return false;
        return blinks == that.blinks && Objects.equals(stone, that.stone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blinks, stone);
    }
}
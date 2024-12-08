package com.cheung.timothy.day08;

import com.cheung.timothy.day06.Coord;
import com.cheung.timothy.day06.GuardGallivantPart1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class ResonantCollinearityPart1 {

    public static void main(String[] args) throws IOException {
        ClassLoader classLoader = GuardGallivantPart1.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream("ResonantCollinearity/input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            Map<Character, List<Coord>> antennae = new HashMap<>();
            int row = 0;
            int col = 0;
            while ((line = reader.readLine()) != null) {
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) != '.') {
                        Character antenna = line.charAt(i);
                        if (antennae.containsKey(antenna)) {
                            antennae.get(antenna).add(new Coord(i, row));
                        } else {
                            List<Coord> antennaList = new ArrayList<>();
                            antennaList.add(new Coord(i, row));
                            antennae.put(antenna, antennaList);
                        }
                    }
                }
                col = line.length();
                row++;
            }

            Set<Coord> antinodes = new HashSet<>();
            for (List<Coord> antennaGroups : antennae.values()) {
                for (Coord first : antennaGroups) {
                    for (Coord second : antennaGroups) {
                        if (first.equals(second)) {
                            continue;
                        }
                        int dx = first.getX() - second.getX();
                        int dy = first.getY() - second.getY();
                        Coord firstAntinode = new Coord(first.getX() + dx, first.getY() + dy);
                        Coord secondAntinode = new Coord(second.getX() - dx, second.getY() - dy);
                        if (isInBounds(firstAntinode, col, row)) {
                            antinodes.add(firstAntinode);
                        }
                        if (isInBounds(secondAntinode, col, row)) {
                            antinodes.add(secondAntinode);
                        }
                    }
                }
            }

            System.out.println("Total Unique Antinodes: " + antinodes.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isInBounds(Coord coord, int columns, int rows) {
        return coord.getX() < columns && coord.getX() >= 0 && coord.getY() < rows && coord.getY() >= 0;
    }
}

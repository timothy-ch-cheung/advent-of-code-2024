package com.cheung.timothy.day12;

import com.cheung.timothy.day06.Coord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class GardenGroupsPart1 {

    public static void main(String[] args) throws IOException {

        ClassLoader classLoader = GardenGroupsPart1.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream("GardenGroups/example.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            List<List<String>> garden = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                garden.add(Arrays.asList(line.split("")));
            }
            Map<Coord, PlotKey> coordToPlotKey = new HashMap<>();
            Map<PlotKey, PlotStats> stats = new HashMap<>();

            for (int y = 0; y < garden.size(); y++) {
                for (int x = 0; x < garden.get(y).size(); x++) {
                    String currentPlot = garden.get(y).get(x);
                    PlotKey key = getPlotKey(new Coord(x, y), coordToPlotKey, garden);
                    if (!stats.containsKey(key)) {
                        stats.put(key, new PlotStats());
                    }

                    int perimeter = 0;
                    if (x - 1 < 0 || !currentPlot.equals(garden.get(y).get(x - 1))) {
                        perimeter++;
                    }
                    if (x + 1 >= garden.get(y).size() || !currentPlot.equals(garden.get(y).get(x + 1))) {
                        perimeter++;
                    }
                    if (y - 1 < 0 || !currentPlot.equals(garden.get(y - 1).get(x))) {
                        perimeter++;
                    }
                    if (y + 1 >= garden.size() || !currentPlot.equals(garden.get(y + 1).get(x))) {
                        perimeter++;
                    }

                    stats.get(key).incrementStats(1, perimeter);
                }
            }

            int totalCost = 0;
            for (PlotStats plotStat: stats.values()) {
                totalCost += plotStat.getFenceCost();
            }

            System.out.println("Fence Cost: " + totalCost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static PlotKey getPlotKey(Coord plotCoord, Map<Coord, PlotKey> coordPlotKeyMap, List<List<String>> garden) {
        String gardenPlot = garden.get(plotCoord.getY()).get(plotCoord.getX());
        Coord plotUp = new Coord(plotCoord.getX(), plotCoord.getY() - 1);
        Coord plotLeft = new Coord(plotCoord.getX() - 1, plotCoord.getY());

        PlotKey plotUpKey = coordPlotKeyMap.get(plotUp);
        PlotKey plotLeftKey = coordPlotKeyMap.get(plotLeft);
        if (plotUpKey != null && gardenPlot.equals(plotUpKey.getKey()) && coordPlotKeyMap.containsKey(plotUp)) {
            coordPlotKeyMap.put(plotCoord, plotUpKey);
            return plotUpKey;
        } else if (plotLeftKey != null && gardenPlot.equals(plotLeftKey.getKey()) && coordPlotKeyMap.containsKey(plotLeft)) {
            coordPlotKeyMap.put(plotCoord, plotLeftKey);
            return plotLeftKey;
        }
        PlotKey key = new PlotKey(garden.get(plotCoord.getY()).get(plotCoord.getX()), UUID.randomUUID().toString());
        coordPlotKeyMap.put(plotCoord, key);
        return key;
    }
}

class PlotKey {
    private String key;
    private String uuid;

    public PlotKey(String key, String uuid) {
        this.key = key;
        this.uuid = uuid;
    }

    public String getKey() {
        return key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlotKey plotKey)) return false;
        return Objects.equals(key, plotKey.key) && Objects.equals(uuid, plotKey.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, uuid);
    }
}

class PlotStats {
    private int area;
    private int perimeter;

    public void incrementStats(int areaIncrement, int perimeterIncrement) {
        area += areaIncrement;
        perimeter += perimeterIncrement;
    }

    public int getFenceCost() {
        return area * perimeter;
    }
}
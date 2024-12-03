package com.cheung.timothy.day01;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class HistorianHysteriaPart1 {

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("src/main/resources/HistorianHysteria/input.txt");

        try {
            List<String> lines = Files.readAllLines(path);
            int[] leftList = new int[lines.size()];
            int[] rightList = new int[lines.size()];

            for (int i = 0; i < lines.size(); i++) {
                String[] splitLine = lines.get(i).split("   ");
                leftList[i] = Integer.parseInt(splitLine[0]);
                rightList[i] = Integer.parseInt(splitLine[1]);
            }

            Arrays.sort(leftList);
            Arrays.sort(rightList);
            int distance = 0;
            for (int i = 0; i < lines.size(); i++) {
                distance += Math.abs(leftList[i] - rightList[i]);
            }
            System.out.println("Total Distance: " + distance);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

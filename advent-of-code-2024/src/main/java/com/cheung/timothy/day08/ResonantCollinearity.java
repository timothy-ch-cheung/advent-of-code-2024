package com.cheung.timothy.day08;

import com.cheung.timothy.day06.GuardGallivantPart1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class ResonantCollinearity {

    public static void main(String[] args) throws IOException {
        ClassLoader classLoader = GuardGallivantPart1.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream("BridgeRepair/input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            long totalCalibration = 0;
            while ((line = reader.readLine()) != null) {

            }
            System.out.println("Total Calibration: " + totalCalibration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

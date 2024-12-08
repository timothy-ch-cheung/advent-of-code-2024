package com.cheung.timothy.day07;

import com.cheung.timothy.day06.GuardGallivantPart1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class BridgeRepairPart1 {

    public static void main(String[] args) throws IOException {

        ClassLoader classLoader = GuardGallivantPart1.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream("BridgeRepair/input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            long totalCalibration = 0;
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(":");
                long total = Long.parseLong(row[0]);
                List<Long> nums = Arrays.stream(row[1].trim().split(" ")).map(Long::parseLong).toList();
                if (isValidTest(total, nums.size() - 1, nums)) {
                    totalCalibration += total;
                }
            }
            System.out.println("Total Calibration: " + totalCalibration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isValidTest(long currentTotal, int currentIdx, List<Long> nums) {
        if (currentIdx == 0) {
            if (currentTotal - nums.get(0) == 0) {
                return true;
            } else if (currentTotal / nums.get(0) == 0 && currentTotal % nums.get(0) == 0) {
                return true;
            }
            return false;
        }
        return isValidTest(currentTotal - nums.get(currentIdx), currentIdx - 1, nums) ||
                (currentTotal % nums.get(currentIdx) == 0 && isValidTest(currentTotal / nums.get(currentIdx), currentIdx - 1, nums));
    }
}

package com.cheung.timothy.day07;

import com.cheung.timothy.day06.GuardGallivantPart1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class BridgeRepairPart2 {

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

    private static boolean isValidTest(long currentTotal, int currentIdx, List<Long> nums) {
        if (currentIdx == 0) {
            if (currentTotal - nums.get(0) == 0) {
                return true;
            } else if (currentTotal / nums.get(0) == 0 && currentTotal % nums.get(0) == 0) {
                return true;
            } else if (canDeconcatenate(currentTotal, nums.get(currentIdx)) && deConcatenate(currentTotal, nums.get(currentIdx)) == 0) {
                return true;
            }
            return false;
        }
        return isValidTest(currentTotal - nums.get(currentIdx), currentIdx - 1, nums) ||
                (currentTotal % nums.get(currentIdx) == 0 && isValidTest(currentTotal / nums.get(currentIdx), currentIdx - 1, nums)) ||
                (canDeconcatenate(currentTotal, nums.get(currentIdx)) && isValidTest(deConcatenate(currentTotal, nums.get(currentIdx)), currentIdx - 1, nums));
    }

    private static boolean canDeconcatenate(long first, long second) {
        String firstStr = Long.toString(first);
        String secondStr = Long.toString(second);
        if (secondStr.length() > firstStr.length()) {
            return false;
        }
        return secondStr.equals(firstStr.substring(firstStr.length() - secondStr.length()));
    }

    private static long deConcatenate(long first, long second) {
        if (first < 0) {
            return Long.MAX_VALUE;
        }
        String firstStr = Long.toString(first);
        String secondStr = Long.toString(second);
        String deconcatenated = firstStr.substring(0, firstStr.length() - secondStr.length());
        if ("".equals(deconcatenated)) {
            return 0;
        }
        return Long.parseLong(deconcatenated);
    }
}

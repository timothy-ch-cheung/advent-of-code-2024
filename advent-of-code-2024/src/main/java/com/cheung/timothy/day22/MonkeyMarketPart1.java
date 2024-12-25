package com.cheung.timothy.day22;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MonkeyMarketPart1 {

    public static void main(String[] args) throws IOException {

        ClassLoader classLoader = MonkeyMarketPart1.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream("MonkeyMarket/input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            long total = 0;
            while ((line = reader.readLine()) != null) {
                long secretNumber = Long.parseLong(line);
                for (int i = 0; i < 2000; i++) {
                    secretNumber = nextSecretNumber(secretNumber);
                }
                total += secretNumber;
            }

            System.out.println("Total Secret: " + total);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static long nextSecretNumber(long secretNumber) {
        long step1 = prune(mix(secretNumber * 64L, secretNumber));
        long step2 = mix(step1/32L, step1);
        return prune(mix(step2 * 2048L, step2));
    }

    private static long mix(long num1, long num2) {
        return num1 ^ num2;
    }

    private static long prune(long num) {
        return num % 16777216L;
    }
}

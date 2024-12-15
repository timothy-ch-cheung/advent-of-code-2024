package com.cheung.timothy.day13;

import com.cheung.timothy.day06.Coord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClawContraptionPart2 {

    private static final Pattern BUTTON_A_REGEX = Pattern.compile("Button A: X\\+?(-?\\d+), Y\\+?(-?\\d+)");
    private static final Pattern BUTTON_B_REGEX = Pattern.compile("Button B: X\\+?(-?\\d+), Y\\+?(-?\\d+)");
    private static final Pattern PRIZE_REGEX = Pattern.compile("Prize: X=(\\d+), Y=(\\d+)");
    private static final long CORRECTION = 10000000000000L;

    public static void main(String[] args) throws IOException {

        ClassLoader classLoader = ClawContraptionPart1.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream("ClawContraption/input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            long totalTokens = 0;
            while ((line = reader.readLine()) != null) {
                Matcher aButtonMatcher = BUTTON_A_REGEX.matcher(line);
                aButtonMatcher.matches();
                LongDelta aMove = new LongDelta(Long.parseLong(aButtonMatcher.group(1)), Long.parseLong(aButtonMatcher.group(2)));

                line = reader.readLine();
                Matcher bButtonMatcher = BUTTON_B_REGEX.matcher(line);
                bButtonMatcher.matches();
                LongDelta bMove = new LongDelta(Long.parseLong(bButtonMatcher.group(1)), Long.parseLong(bButtonMatcher.group(2)));

                line = reader.readLine();
                Matcher prizeMatcher = PRIZE_REGEX.matcher(line);
                prizeMatcher.matches();
                LongCoord prizeCoord = new LongCoord(
                        Long.parseLong(prizeMatcher.group(1)) + CORRECTION,
                        Long.parseLong(prizeMatcher.group(2)) + CORRECTION
                );

                totalTokens += numTokens(aMove, bMove, prizeCoord);
                reader.readLine();
            }

            System.out.println("Total Tokens: " + totalTokens);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static long numTokens(LongDelta aMove, LongDelta bMove, LongCoord prize) {
        long det = aMove.getX() * bMove.getY() - aMove.getY() * bMove.getX();
        if (det == 0) {
            return 0;
        }

        long detA = prize.getX() * bMove.getY() - prize.getY() * bMove.getX();
        long a = detA / det;

        long detB = aMove.getX() * prize.getY() - aMove.getY() * prize.getX();
        long b = detB / det;

        if (detA % det == 0 && detB % det == 0) {
            return 3 * a + b;
        }
        return 0;
    }
}

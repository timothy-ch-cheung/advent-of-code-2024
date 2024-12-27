package com.cheung.timothy.day21;

import com.cheung.timothy.day13.ClawContraptionPart1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class KeypadConundrumPart1 {

    public static void main(String[] args) throws IOException {

        ClassLoader classLoader = ClawContraptionPart1.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream("KeypadConundrum/example.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            int totalComplexity = 0;
            while ((line = reader.readLine()) != null) {
                List<DirectionalKeypadValue> robot1Moves = numericToDirectional(line);
                List<DirectionalKeypadValue> robot2Moves = directionalToDirectional(robot1Moves);
                List<DirectionalKeypadValue> humanMoves = directionalToDirectional(robot2Moves);

//                printMoves(robot1Moves);
//                printMoves(robot2Moves);
//                printMoves(humanMoves);
                int complexity = Integer.parseInt(line.substring(0, line.length() - 1)) * humanMoves.size();
                System.out.println(line + ": " + complexity);
                totalComplexity += complexity;
            }

            System.out.println("Total Complexity: " + totalComplexity);
            printMoves(numericToDirectional("379A"));
            printMoves(directionalToDirectional(numericToDirectional("379A")));
            printMoves(directionalToDirectional(directionalToDirectional(numericToDirectional("379A"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<DirectionalKeypadValue> numericToDirectional(String input) {
        List<DirectionalKeypadValue> moves = new ArrayList<>();
        NumericKeypadValue position = NumericKeypadValue.A_BUTTON;
        for (int i = 0; i < input.length(); i++) {
            NumericKeypadValue target = NumericKeypadValue.fromString(input.charAt(i));
            assert target != null;
            moves.addAll(position.move(target));
            moves.add(DirectionalKeypadValue.A_BUTTON);
            position = target;
        }
        return moves;
    }

    private static List<DirectionalKeypadValue> directionalToDirectional(List<DirectionalKeypadValue> originalMoves) {
        List<DirectionalKeypadValue> moves = new ArrayList<>();
        DirectionalKeypadValue position = DirectionalKeypadValue.A_BUTTON;
        for (int i = 0; i < originalMoves.size(); i++) {
            moves.addAll(position.move(originalMoves.get(i)));
            moves.add(DirectionalKeypadValue.A_BUTTON);
            position = originalMoves.get(i);
        }
        return moves;
    }

    private static List<DirectionalKeypadValue> fromString(String input) {
        List<DirectionalKeypadValue> moves = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            moves.add(DirectionalKeypadValue.fromChar(input.charAt(i)));
        }
        return moves;
    }

    private static void printMoves(List<DirectionalKeypadValue> keypadValues) {
        for (DirectionalKeypadValue value: keypadValues) {
            System.out.print(value);
        }
        System.out.println();
    }
}

package com.cheung.timothy.day03;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MullItOverPart2 {

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("src/main/resources/MullItOver/input.txt");

        try {
            String input = String.join("", Files.readAllLines(path));
            int total = 0;
            Pattern operationPattern = Pattern.compile("(mul\\(\\d{1,3},\\d{1,3}\\))|(do\\(\\))|(don't\\(\\))");
            Matcher operationMatcher = operationPattern.matcher(input);
            List<String> matches = new ArrayList<>();
            while (operationMatcher.find()) {
                matches.add(operationMatcher.group());
            }
            boolean skip = false;
            Pattern numberPattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
            for (String operation: matches) {
                if ("do()".equals(operation)) {
                    skip = false;
                    continue;
                } else if ("don't()".equals(operation)) {
                    skip = true;
                    continue;
                } else if (skip) {
                    continue;
                }
                Matcher numberMatcher = numberPattern.matcher(operation);
                numberMatcher.find();
                int num1 = Integer.parseInt(numberMatcher.group(1));
                int num2 = Integer.parseInt(numberMatcher.group(2));
                total += num1 * num2;
            }


            System.out.println("Total: " + total);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

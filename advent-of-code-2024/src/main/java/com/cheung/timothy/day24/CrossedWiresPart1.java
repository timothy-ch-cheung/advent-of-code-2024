package com.cheung.timothy.day24;

import com.cheung.timothy.day23.LanPartyPart1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CrossedWiresPart1 {

    private static final Pattern EQUATION_REGEX = Pattern.compile("([a-z0-9]{3}) (AND|OR|XOR) ([a-z0-9]{3}) -> ([a-z0-9]{3})");

    public static void main(String[] args) throws IOException {

        ClassLoader classLoader = LanPartyPart1.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream("CrossedWires/input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            Map<String, Integer> constants = new HashMap<>();
            while ((line = reader.readLine()) != null && !"".equals(line)) {
                String[] curr = line.split(":");
                constants.put(curr[0], Integer.parseInt(curr[1].trim()));
            }

            Set<Equation> equations = new HashSet<>();
            while ((line = reader.readLine()) != null) {
                Matcher equationMatcher = EQUATION_REGEX.matcher(line);
                equationMatcher.matches();
                equations.add(new Equation(equationMatcher.group(1), equationMatcher.group(3), equationMatcher.group(2), equationMatcher.group(4)));
            }

            while (!equations.isEmpty()) {
                List<Equation> toRemove = new ArrayList<>();
                for (Equation equation: equations) {
                    if (constants.containsKey(equation.getX1()) && constants.containsKey(equation.getX2())) {
                        toRemove.add(equation);
                        var x1 = constants.get(equation.getX1());
                        var x2 = constants.get(equation.getX2());
                        switch (equation.getOperator()) {
                            case "XOR" -> {
                                constants.put(equation.getResult(), x1 ^ x2);
                            }
                            case "AND" -> {
                                constants.put(equation.getResult(), x1 & x2);
                            }
                            case "OR" -> {
                                constants.put(equation.getResult(), x1 | x2);
                            }
                        }
                    }
                }
                for (Equation equation: toRemove) {
                    equations.remove(equation);
                }
            }

            List<Map.Entry<String, Integer>> result =  constants.entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith("z"))
                    .sorted(Map.Entry.comparingByKey()).toList();
            String binary =result.stream().map(Map.Entry::getValue).map(Object::toString).collect(Collectors.joining());
            long number = new BigInteger(new StringBuilder(binary).reverse().toString(), 2).longValue();
            System.out.println("Secret Value: " + number);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Equation {
    private final String x1;
    private final String x2;
    private final String operator;
    private final String result;

    public Equation(String x1, String x2, String operator, String result) {
        this.x1 =  x1;
        this.x2 = x2;
        this.operator = operator;
        this.result = result;
    }

    public String getX1() {
        return x1;
    }

    public String getX2() {
        return x2;
    }

    public String getOperator() {
        return operator;
    }

    public String getResult() {
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Equation equation)) return false;
        return Objects.equals(x1, equation.x1) &&
                Objects.equals(x2, equation.x2) &&
                Objects.equals(operator, equation.operator) &&
                Objects.equals(result, equation.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x1, x2, operator, result);
    }
}

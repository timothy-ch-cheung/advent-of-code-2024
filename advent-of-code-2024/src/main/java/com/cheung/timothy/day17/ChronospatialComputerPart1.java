package com.cheung.timothy.day17;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class ChronospatialComputerPart1 {

    public static void main(String[] args) throws IOException {

        ClassLoader classLoader = ChronospatialComputerPart1.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream("ChronospatialComputer/input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            var registerA = Long.parseLong(reader.readLine().split(" ")[2]);
            var registerB = Long.parseLong(reader.readLine().split(" ")[2]);
            var registerC = Long.parseLong(reader.readLine().split(" ")[2]);

            Computer computer = new Computer(registerA, registerB, registerC);

            reader.readLine();
            List<Integer> instructions = Arrays.stream(reader.readLine().split(" ")[1].split(",")).map(Integer::parseInt).toList();
            computer.run(instructions);
            System.out.println();
            System.out.println("DONE!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


class Computer {

    private long registerA;
    private long registerB;
    private long registerC;
    private int instructionPointer;

    public Computer(long registerA, long registerB, long registerC) {
        this.registerA = registerA;
        this.registerB = registerB;
        this.registerC = registerC;
    }

    public void run(List<Integer> instructions) {
        instructionPointer = 0;

        while (instructionPointer < instructions.size()) {
            boolean jumped = false;
            switch (instructions.get(instructionPointer)) {
                case 0 -> adv(instructions.get(instructionPointer + 1));
                case 1 -> bxl(instructions.get(instructionPointer + 1));
                case 2 -> bst(instructions.get(instructionPointer + 1));
                case 3 -> jumped = jnz(instructions.get(instructionPointer + 1));
                case 4 -> bxc(instructions.get(instructionPointer + 1));
                case 5 -> out(instructions.get(instructionPointer + 1));
                case 6 -> bdv(instructions.get(instructionPointer + 1));
                case 7 -> cdv(instructions.get(instructionPointer + 1));
                default -> throw new RuntimeException();
            }

            if (!jumped) {
                instructionPointer += 2;
            }
        }
    }

    private void adv(int operand) {
        registerA = registerA / powerN(2, comboOperand(operand));
    }

    private void bxl(int operand) {
        registerB = registerB ^ operand;
    }

    private void bst(int operand) {
        registerB = comboOperand(operand) % 8;
    }

    private boolean jnz(int operand) {
        if (registerA != 0) {
            instructionPointer = operand;
            return true;
        }
        return false;
    }

    private void bxc(int operand) {
        registerB = registerB ^ registerC;
    }

    private void out(int operand) {
        long out = comboOperand(operand) % 8;
        //System.out.println("OUT: " + out);
        System.out.print(out);
    }

    private void bdv(int operand) {
        registerB = registerA / powerN(2, comboOperand(operand));
    }

    private void cdv(int operand) {
        registerC = registerA / powerN(2, comboOperand(operand));
    }

    private long comboOperand(int operand) {
        return switch (operand) {
            case 0, 1, 2, 3 -> operand;
            case 4 -> registerA;
            case 5 -> registerB;
            case 6 -> registerC;
            default -> throw new RuntimeException();
        };
    }

    public long powerN(long number, long power) {
        long result = 1;
        while (power > 0) {
            result *= number;
            power--;
        }
        return result;
    }
}
package com.moving_system;

import java.util.Scanner;

public class InputValidationHelper {
    private static InputValidationHelper instance;

    private final Scanner scanner;

    public InputValidationHelper(final Scanner scanner) {
        this.scanner = scanner;
    }

    // Singleton accessor
    public static InputValidationHelper getInstance(final Scanner scanner) {
        if (instance == null) {
            instance = new InputValidationHelper(scanner);
        }
        return instance;
    }

    public <T> String getValidInput(String prompt, InputParser<T> parser, InputValidator<T> validator) {
        String input;
        T parsedInput;

        do {
            System.out.print("Enter " + prompt + ": ");
            input = scanner.nextLine();

            try {
                parsedInput = parser.parse(input);
                if (validator.isValid(parsedInput)) {
                    return input;
                } else {
                    System.out.println("Invalid input. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input format. Please try again.");
            }
        } while (true);
    }
}

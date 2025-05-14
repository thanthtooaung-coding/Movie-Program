package com.moving_system;

import java.util.Scanner;

/**
 * Main application class implementing the Movie Information System
 */
public class Main {
    private static Main instance;

    private static final String CSV_FILE_PATH = "data/movies.csv";
    private final Scanner scanner;
    private final IMovieDao movieDao;
    private final IMovieValidator movieValidator;

    public Main() {
        this.scanner = new Scanner(System.in);
        this.movieDao = CsvMovieDao.getInstance(CSV_FILE_PATH);
        this.movieValidator = MovieValidator.getInstance(movieDao);
    }

    public static Main getInstance() {
        if (instance == null) {
            instance = new Main();
        }
        return instance;
    }

    public void start() {
        boolean running = true;

        while (running) {
            displayMenu();
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> AddMovie.getInstance(movieDao, movieValidator).execute();
                case "2" -> UpdateMovie.getInstance(movieDao, movieValidator).execute();
                case "3" -> DeleteMovie.getInstance(movieDao).execute();
                case "4" -> GenerateReports.getInstance(movieDao).execute();
                case "5" -> DisplayAllMovies.getInstance(movieDao).execute();
                case "6" -> {
                    System.out.println("Thank you for using the Movie Information System!");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n=== Movie Information System ===");
        System.out.println("1. Add New Movie");
        System.out.println("2. Update Movie");
        System.out.println("3. Delete Movie");
        System.out.println("4. Generate Reports");
        System.out.println("5. Display All Movies");
        System.out.println("6. Exit");
    }

    public static void main(String[] args) {
        final Main app = Main.getInstance();
        app.start();
    }
}
package com.moving_system;

import java.util.Scanner;

/**
 * Main application class implementing the Movie Information System
 */
public class Main {
    private static final String CSV_FILE_PATH = "data/movies.csv";
    private final Scanner scanner;
    private final MovieServiceImpl movieServiceImpl;

    public Main() {
        this.scanner = new Scanner(System.in);
        final IMovieDao movieDao = new CsvMovieDao(CSV_FILE_PATH);
        final IMovieValidator movieValidator = new MovieValidator(movieDao);
        this.movieServiceImpl = new MovieServiceImpl(movieDao, movieValidator);
    }

    public void start() {
        boolean running = true;

        while (running) {
            displayMenu();
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    movieServiceImpl.addMovie();
                    break;
                case "2":
                    movieServiceImpl.updateMovie();
                    break;
                case "3":
                    movieServiceImpl.deleteMovie();
                    break;
                case "4":
                    movieServiceImpl.generateReports();
                    break;
                case "5":
                    movieServiceImpl.displayAllMovies();
                    break;
                case "6":
                    System.out.println("Thank you for using the Movie Information System!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
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
        Main app = new Main();
        app.start();
    }
}
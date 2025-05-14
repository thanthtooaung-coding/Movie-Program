package com.moving_system;

import java.util.List;
import java.util.Scanner;

public class GenerateReports {
    private static GenerateReports instance;
    private final IMovieDao movieDao;
    private final Scanner scanner;

    private GenerateReports(IMovieDao movieDao) {
        this.movieDao = movieDao;
        this.scanner = new Scanner(System.in);
    }

    // Singleton accessor
    public static GenerateReports getInstance(IMovieDao movieDao) {
        if (instance == null) {
            instance = new GenerateReports(movieDao);
        }
        return instance;
    }

    public void execute() {
        System.out.println("\n=== Generate Reports ===");
        System.out.println("1. Movies in descending order of runtime");
        System.out.println("2. Movies of selected genre sorted by title");
        System.out.println("3. Number of movies by production country");
        System.out.print("Enter your choice: ");

        String choice = scanner.nextLine();
        List<Movie> movies = movieDao.getAllMovies();

        switch (choice) {
            case "1":
                new RuntimeReport().generateReport(movies);
                break;
            case "2":
                new GenreReport().generateReport(movies);
                break;
            case "3":
                new CountryReport().generateReport(movies);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
}

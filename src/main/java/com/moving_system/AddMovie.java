package com.moving_system;

import java.util.Scanner;

public class AddMovie {
    private static AddMovie instance;
    private final IMovieDao movieDao;
    private final IMovieValidator validator;
    private final Scanner scanner;

    private AddMovie(IMovieDao movieDao, IMovieValidator validator) {
        this.movieDao = movieDao;
        this.validator = validator;
        this.scanner = new Scanner(System.in);
    }

    // Singleton accessor
    public static AddMovie getInstance(IMovieDao movieDao, IMovieValidator validator) {
        if (instance == null) {
            instance = new AddMovie(movieDao, validator);
        }
        return instance;
    }

    public void execute() {
        System.out.println("\n=== Add New Movie ===");

        // Get and validate movie ID
        int id;
        do {
            System.out.print("Enter ID: ");
            id = Integer.parseInt(scanner.nextLine());

            if (!validator.isValidId(id)) {
                System.out.println("Invalid ID. ID must be positive.");
                continue;
            }

            if (!validator.isIdUnique(id)) {
                System.out.println("A movie with this ID already exists.");
                continue;
            }

            break;
        } while (true);

        final String title = InputValidationHelper.getInstance(scanner).getValidInput("Title", s -> s, validator::isValidTitle);
        final String releaseDate = InputValidationHelper.getInstance(scanner).getValidInput("Release Date (MM/dd/yyyy)", s -> s, validator::isValidReleaseDate);
        final String revenueStr = InputValidationHelper.getInstance(scanner).getValidInput("Revenue", Long::parseLong, validator::isValidRevenue);
        final long revenue = Long.parseLong(revenueStr);
        final String runtimeStr = InputValidationHelper.getInstance(scanner).getValidInput("Runtime (minutes)", Integer::parseInt, validator::isValidRuntime);
        final int runtime = Integer.parseInt(runtimeStr);
        final String companie = InputValidationHelper.getInstance(scanner).getValidInput("Company", s -> s, validator::isValidCompanie);
        final String genre = InputValidationHelper.getInstance(scanner).getValidInput("Genre", s -> s, validator::isValidGenre);
        final String prodCountry = InputValidationHelper.getInstance(scanner).getValidInput("Production Country", s -> s, validator::isValidProdCountry);
        final String voteAvgStr = InputValidationHelper.getInstance(scanner).getValidInput("Vote Average (0-10)", Double::parseDouble, validator::isValidVoteAverage);
        final double voteAverage = Double.parseDouble(voteAvgStr);

        final Movie movie = new Movie(id, title, releaseDate, revenue, runtime, companie, genre, prodCountry, voteAverage);

        if (movieDao.addMovie(movie)) {
            System.out.println("Movie added successfully!");
            this.movieDao.saveToFile();
        } else {
            System.out.println("Failed to add movie. Please try again.");
        }
    }
}

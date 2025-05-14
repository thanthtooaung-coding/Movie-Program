package com.moving_system;

import java.util.Scanner;

public class UpdateMovie {
    private static UpdateMovie instance;
    private final IMovieDao movieDao;
    private final IMovieValidator validator;
    private final Scanner scanner;

    private UpdateMovie(IMovieDao movieDao, IMovieValidator validator) {
        this.movieDao = movieDao;
        this.validator = validator;
        this.scanner = new Scanner(System.in);
    }

    // Singleton accessor
    public static UpdateMovie getInstance(IMovieDao movieDao, IMovieValidator validator) {
        if (instance == null) {
            instance = new UpdateMovie(movieDao, validator);
        }
        return instance;
    }

    public void execute() {
        System.out.println("\n=== Update Movie ===");

        System.out.print("Enter the ID of the movie to update: ");
        int id = Integer.parseInt(scanner.nextLine());

        Movie existingMovie = movieDao.getMovieById(id);
        if (existingMovie == null) {
            System.out.println("No movie found with ID: " + id);
            return;
        }

        System.out.println("Updating movie: " + existingMovie.getTitle());

        final String title = InputValidationHelper.getInstance(scanner).getValidInput("Title [" + existingMovie.getTitle() + "]",
                input -> input.isEmpty() ? existingMovie.getTitle() : input,
                input -> input.isEmpty() || validator.isValidTitle(input));

        final String releaseDate = InputValidationHelper.getInstance(scanner).getValidInput("Release Date [" + existingMovie.getReleaseDate() + "]",
                input -> input.isEmpty() ? existingMovie.getReleaseDate() : input,
                input -> input.isEmpty() || validator.isValidReleaseDate(input));

        final String revenueStr = InputValidationHelper.getInstance(scanner).getValidInput("Revenue [" + existingMovie.getRevenue() + "]",
                input -> input.isEmpty() ? String.valueOf(existingMovie.getRevenue()) : input,
                input -> input.isEmpty() || validator.isValidRevenue(Long.parseLong(input)));
        final long revenue = Long.parseLong(revenueStr);

        final String runtimeStr = InputValidationHelper.getInstance(scanner).getValidInput("Runtime [" + existingMovie.getRuntime() + "]",
                input -> input.isEmpty() ? String.valueOf(existingMovie.getRuntime()) : input,
                input -> input.isEmpty() || validator.isValidRuntime(Integer.parseInt(input)));
        final int runtime = Integer.parseInt(runtimeStr);

        final String companie = InputValidationHelper.getInstance(scanner).getValidInput("Company [" + existingMovie.getCompanie() + "]",
                input -> input.isEmpty() ? existingMovie.getCompanie() : input,
                input -> input.isEmpty() || validator.isValidCompanie(input));

        final String genre = InputValidationHelper.getInstance(scanner).getValidInput("Genre [" + existingMovie.getGenre() + "]",
                input -> input.isEmpty() ? existingMovie.getGenre() : input,
                input -> input.isEmpty() || validator.isValidGenre(input));

        final String prodCountry = InputValidationHelper.getInstance(scanner).getValidInput("Production Country [" + existingMovie.getProdCountry() + "]",
                input -> input.isEmpty() ? existingMovie.getProdCountry() : input,
                input -> input.isEmpty() || validator.isValidProdCountry(input));

        final String voteAverageStr = InputValidationHelper.getInstance(scanner).getValidInput("Vote Average [" + existingMovie.getVoteAverage() + "]",
                input -> input.isEmpty() ? String.valueOf(existingMovie.getVoteAverage()) : input,
                input -> input.isEmpty() || validator.isValidVoteAverage(Double.parseDouble(input)));
        final double voteAverage = Double.parseDouble(voteAverageStr);

        Movie updatedMovie = new Movie(id, title, releaseDate, revenue, runtime, companie, genre, prodCountry, voteAverage);

        if (movieDao.updateMovie(updatedMovie)) {
            System.out.println("Movie updated successfully!");
            movieDao.saveToFile();
        } else {
            System.out.println("Failed to update movie. Please try again.");
        }
    }
}

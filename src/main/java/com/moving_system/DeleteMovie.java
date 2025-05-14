package com.moving_system;

import java.util.Scanner;

public class DeleteMovie {
    private static DeleteMovie instance;
    private final IMovieDao movieDao;
    private final Scanner scanner;

    private DeleteMovie(IMovieDao movieDao) {
        this.movieDao = movieDao;
        this.scanner = new Scanner(System.in);
    }

    // Singleton accessor
    public static DeleteMovie getInstance(IMovieDao movieDao) {
        if (instance == null) {
            instance = new DeleteMovie(movieDao);
        }
        return instance;
    }

    public void execute() {
        System.out.println("\n=== Delete Movie ===");

        System.out.print("Enter the ID of the movie to delete: ");
        int id = Integer.parseInt(scanner.nextLine());

        Movie existingMovie = movieDao.getMovieById(id);
        if (existingMovie == null) {
            System.out.println("No movie found with ID: " + id);
            return;
        }

        System.out.println("Movie found: " + existingMovie.getTitle());
        System.out.print("Are you sure you want to delete this movie? (y/n): ");
        String confirmation = scanner.nextLine();

        if (confirmation.toLowerCase().startsWith("y")) {
            if (movieDao.deleteMovie(id)) {
                System.out.println("Movie deleted successfully!");
                movieDao.saveToFile();
            } else {
                System.out.println("Failed to delete movie. Please try again.");
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
    }
}

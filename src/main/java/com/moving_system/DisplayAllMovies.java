package com.moving_system;

import de.vandermeer.asciitable.AsciiTable;

import java.util.List;
import java.util.Scanner;

public class DisplayAllMovies {
    private static DisplayAllMovies instance;
    private final IMovieDao movieDao;

    private DisplayAllMovies(IMovieDao movieDao) {
        this.movieDao = movieDao;
    }

    // Singleton accessor
    public static DisplayAllMovies getInstance(IMovieDao movieDao) {
        if (instance == null) {
            instance = new DisplayAllMovies(movieDao);
        }
        return instance;
    }

    public void execute() {
        List<Movie> movies = movieDao.getAllMovies();

        if (movies.isEmpty()) {
            System.out.println("No movies found.");
            return;
        }

        System.out.println("\n=== All Movies ===");

        AsciiTable table = new de.vandermeer.asciitable.AsciiTable();
        table.addRule();
        table.addRow("ID", "Title", "Release Date", "Runtime", "Vote Avg");
        table.addRule();

        for (Movie movie : movies) {
            table.addRow(
                    movie.getId(),
                    movie.getTitle(),
                    movie.getReleaseDate(),
                    movie.getRuntime(),
                    movie.getVoteAverage()
            );
            table.addRule();
        }

        System.out.println(table.render());
    }
}

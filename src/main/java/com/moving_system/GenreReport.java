package com.moving_system;

import de.vandermeer.asciitable.AsciiTable;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Report showing movies of a selected genre sorted by title
 */
public class GenreReport extends AbstractReport {
    private String selectedGenre;
    
    @Override
    protected List<Movie> filterMovies(List<Movie> movies) {
        System.out.print("Enter genre to filter movies: ");
        selectedGenre = scanner.nextLine().trim();
        
        return movies.stream()
            .filter(movie -> movie.getGenre().toLowerCase().contains(selectedGenre.toLowerCase()))
            .collect(Collectors.toList());
    }
    
    @Override
    protected void processMovies(List<Movie> movies) {
        // Sort by title
        movies.sort(Comparator.comparing(Movie::getTitle));
    }
    
    @Override
    protected void displayResults(List<Movie> movies) {
        System.out.println("\nMovies in genre '" + selectedGenre + "' sorted by title:");
        
        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow("ID", "Title", "Genre", "Vote Average");
        table.addRule();
        
        for (Movie movie : movies) {
            table.addRow(
                movie.getId(),
                movie.getTitle(),
                movie.getGenre(),
                movie.getVoteAverage()
            );
            table.addRule();
        }
        
        System.out.println(table.render());
    }
}
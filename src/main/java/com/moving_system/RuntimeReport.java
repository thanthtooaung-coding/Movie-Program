package com.moving_system;

import de.vandermeer.asciitable.AsciiTable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Report showing movies in descending order of runtime
 */
public class RuntimeReport extends AbstractReport {
    
    @Override
    protected List<Movie> filterMovies(List<Movie> movies) {
        // No filtering for this report, use all movies
        return movies;
    }
    
    @Override
    protected void processMovies(List<Movie> movies) {
        // Sort by runtime in descending order
        movies.sort(Comparator.comparing(Movie::getRuntime).reversed());
    }
    
    @Override
    protected void displayResults(List<Movie> movies) {
        System.out.println("\nMovies in descending order of runtime:");
        
        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow("ID", "Title", "Runtime (min)", "Release Date");
        table.addRule();
        
        for (Movie movie : movies) {
            table.addRow(
                movie.getId(),
                movie.getTitle(),
                movie.getRuntime(),
                movie.getReleaseDate()
            );
            table.addRule();
        }
        
        System.out.println(table.render());
    }
}
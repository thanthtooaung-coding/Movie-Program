package com.moving_system;

import de.vandermeer.asciitable.AsciiTable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Report showing the number of movies from each production country
 */
public class CountryReport extends AbstractReport {
    private Map<String, Integer> countryCount;
    
    @Override
    protected List<Movie> filterMovies(List<Movie> movies) {
        // No filtering for this report, use all movies
        return movies;
    }
    
    @Override
    protected void processMovies(List<Movie> movies) {
        countryCount = new HashMap<>();
        
        for (Movie movie : movies) {
            String country = movie.getProdCountry();
            countryCount.put(country, countryCount.getOrDefault(country, 0) + 1);
        }
    }
    
    @Override
    protected void displayResults(List<Movie> movies) {
        System.out.println("\nNumber of movies by production country:");
        
        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow("Production Country", "Number of Movies");
        table.addRule();
        
        for (Map.Entry<String, Integer> entry : countryCount.entrySet()) {
            table.addRow(entry.getKey(), entry.getValue());
            table.addRule();
        }
        
        System.out.println(table.render());
    }
}
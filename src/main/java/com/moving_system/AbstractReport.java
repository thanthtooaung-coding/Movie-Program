package com.moving_system;

import java.util.List;
import java.util.Scanner;

/**
 * Abstract class for reports following Template Method pattern
 */
public abstract class AbstractReport implements IReportGenerator {
    protected final Scanner scanner;
    
    public AbstractReport() {
        this.scanner = new Scanner(System.in);
    }
    
    // Template method
    @Override
    public final void generateReport(List<Movie> movies) {
        List<Movie> filteredMovies = filterMovies(movies);
        if (filteredMovies.isEmpty()) {
            System.out.println("No movies found matching the criteria.");
            return;
        }
        
        processMovies(filteredMovies);
        displayResults(filteredMovies);
    }

    protected abstract List<Movie> filterMovies(List<Movie> movies);
    protected abstract void processMovies(List<Movie> movies);
    protected abstract void displayResults(List<Movie> movies);
}
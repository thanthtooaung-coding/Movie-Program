package com.moving_system;

import de.vandermeer.asciitable.AsciiTable;

import java.util.List;
import java.util.Scanner;

/**
 * Service class implementing business logic for movie operations
 * Following Single Responsibility Principle
 */
public class MovieServiceImpl implements MovieService {

    private final IMovieDao movieDao;
    private final IMovieValidator validator;
    private final Scanner scanner;
    
    public MovieServiceImpl(IMovieDao movieDao, IMovieValidator validator) {
        this.movieDao = movieDao;
        this.validator = validator;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void addMovie() {
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

        final String title = getValidInput("Title", s -> s, validator::isValidTitle);
        final String releaseDate = getValidInput("Release Date (MM/dd/yyyy)", s -> s, validator::isValidReleaseDate);
        final String revenueStr = getValidInput("Revenue", Long::parseLong, validator::isValidRevenue);
        final long revenue = Long.parseLong(revenueStr);
        final String runtimeStr = getValidInput("Runtime (minutes)", Integer::parseInt, validator::isValidRuntime);
        final int runtime = Integer.parseInt(runtimeStr);
        final String companie = getValidInput("Company", s -> s, validator::isValidCompanie);
        final String genre = getValidInput("Genre", s -> s, validator::isValidGenre);
        final String prodCountry = getValidInput("Production Country", s -> s, validator::isValidProdCountry);
        final String voteAvgStr = getValidInput("Vote Average (0-10)", Double::parseDouble, validator::isValidVoteAverage);
        final double voteAverage = Double.parseDouble(voteAvgStr);

        final Movie movie = new Movie(id, title, releaseDate, revenue, runtime, companie, genre, prodCountry, voteAverage);
        
        if (movieDao.addMovie(movie)) {
            System.out.println("Movie added successfully!");
            this.movieDao.saveToFile();
        } else {
            System.out.println("Failed to add movie. Please try again.");
        }
    }

    @Override
    public void updateMovie() {
        System.out.println("\n=== Update Movie ===");
        
        System.out.print("Enter the ID of the movie to update: ");
        int id = Integer.parseInt(scanner.nextLine());
        
        Movie existingMovie = movieDao.getMovieById(id);
        if (existingMovie == null) {
            System.out.println("No movie found with ID: " + id);
            return;
        }
        
        System.out.println("Updating movie: " + existingMovie.getTitle());

        final String title = getValidInput("Title [" + existingMovie.getTitle() + "]",
                        input -> input.isEmpty() ? existingMovie.getTitle() : input, 
                        input -> input.isEmpty() || validator.isValidTitle(input));

        final String releaseDate = getValidInput("Release Date [" + existingMovie.getReleaseDate() + "]",
                             input -> input.isEmpty() ? existingMovie.getReleaseDate() : input, 
                             input -> input.isEmpty() || validator.isValidReleaseDate(input));

        final String revenueStr = getValidInput("Revenue [" + existingMovie.getRevenue() + "]",
                           input -> input.isEmpty() ? String.valueOf(existingMovie.getRevenue()) : input, 
                           input -> input.isEmpty() || validator.isValidRevenue(Long.parseLong(input)));
        final long revenue = Long.parseLong(revenueStr);

        final String runtimeStr = getValidInput("Runtime [" + existingMovie.getRuntime() + "]",
                          input -> input.isEmpty() ? String.valueOf(existingMovie.getRuntime()) : input, 
                          input -> input.isEmpty() || validator.isValidRuntime(Integer.parseInt(input)));
        final int runtime = Integer.parseInt(runtimeStr);

        final String companie = getValidInput("Company [" + existingMovie.getCompanie() + "]",
                         input -> input.isEmpty() ? existingMovie.getCompanie() : input, 
                         input -> input.isEmpty() || validator.isValidCompanie(input));

        final String genre = getValidInput("Genre [" + existingMovie.getGenre() + "]",
                      input -> input.isEmpty() ? existingMovie.getGenre() : input, 
                      input -> input.isEmpty() || validator.isValidGenre(input));

        final String prodCountry = getValidInput("Production Country [" + existingMovie.getProdCountry() + "]",
                            input -> input.isEmpty() ? existingMovie.getProdCountry() : input, 
                            input -> input.isEmpty() || validator.isValidProdCountry(input));

        final String voteAverageStr = getValidInput("Vote Average [" + existingMovie.getVoteAverage() + "]",
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

    @Override
    public void deleteMovie() {
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

    @Override
    public void displayAllMovies() {
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
    
    public void generateReports() {
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
    
    // Helper method for getting valid input
    private <T> String getValidInput(String prompt, InputParser<T> parser, InputValidator<T> validator) {
        String input;
        T parsedInput;
        
        do {
            System.out.print("Enter " + prompt + ": ");
            input = scanner.nextLine();
            
            try {
                parsedInput = parser.parse(input);
                if (validator.isValid(parsedInput)) {
                    return input;
                } else {
                    System.out.println("Invalid input. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input format. Please try again.");
            }
        } while (true);
    }
    
    // Functional interfaces for input handling
    @FunctionalInterface
    private interface InputParser<T> {
        T parse(String input);
    }
    
    @FunctionalInterface
    private interface InputValidator<T> {
        boolean isValid(T input);
    }
}
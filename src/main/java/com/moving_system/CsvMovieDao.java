package com.moving_system;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of IMovieDao that works with CSV files
 */
public class CsvMovieDao implements IMovieDao {
    private final String csvFilePath;
    private final List<Movie> movies;

    public CsvMovieDao(String csvFilePath) {
        this.csvFilePath = csvFilePath;
        this.movies = new ArrayList<>();
        loadFromFile();
    }

    private void loadFromFile() {
        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            String[] nextLine;
            // Skip header line
            reader.readNext();
            
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine.length == 9) {
                    try {
                        int id = Integer.parseInt(nextLine[0].trim());
                        String releaseDate = nextLine[1].trim();
                        long revenue = Long.parseLong(nextLine[2].trim());
                        double runtime = Double.parseDouble(nextLine[3].trim());
                        String title = nextLine[4].trim();
                        double voteAverage = Double.parseDouble(nextLine[5].trim());
                        String companie = nextLine[6].trim();
                        String genre = nextLine[7].trim();
                        String prodCountry = nextLine[8].trim();

                        Movie movie = new Movie(id, title, releaseDate, revenue, runtime, 
                                               companie, genre, prodCountry, voteAverage);
                        movies.add(movie);
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing line: " + String.join(",", nextLine));
                    }
                }
            }
        } catch (IOException | CsvValidationException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
    }
    
    @Override
    public List<Movie> getAllMovies() {
        return new ArrayList<>(movies);
    }

    @Override
    public Movie getMovieById(int id) {
        for (Movie movie : movies) {
            if (movie.getId() == id) {
                return movie;
            }
        }
        return null;
    }

    @Override
    public boolean addMovie(Movie movie) {
        if (getMovieById(movie.getId()) != null) {
            return false; // Movie with this ID already exists
        }
        movies.add(movie);
        return true;
    }

    @Override
    public boolean updateMovie(Movie updatedMovie) {
        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getId() == updatedMovie.getId()) {
                movies.set(i, updatedMovie);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteMovie(int id) {
        return movies.removeIf(movie -> movie.getId() == id);
    }

    @Override
    public void saveToFile() {
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath))) {
            // Write header
            String[] header = { "id", "title", "release_date", "revenue", "runtime", 
                               "companie", "genre", "prod_country", "vote_average" };
            writer.writeNext(header);
            
            // Write data
            for (Movie movie : movies) {
                String[] data = {
                    String.valueOf(movie.getId()),
                    movie.getTitle(),
                    movie.getReleaseDate(),
                    String.valueOf(movie.getRevenue()),
                    String.valueOf(movie.getRuntime()),
                    movie.getCompanie(),
                    movie.getGenre(),
                    movie.getProdCountry(),
                    String.valueOf(movie.getVoteAverage())
                };
                writer.writeNext(data);
            }
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }
}
package com.moving_system;

import java.util.List;

/**
 * Interface for movie data access operations
 * Following Interface Segregation Principle
 */
public interface IMovieDao {
    List<Movie> getAllMovies();
    Movie getMovieById(int id);
    boolean addMovie(Movie movie);
    boolean updateMovie(Movie movie);
    boolean deleteMovie(int id);
    void saveToFile();
}
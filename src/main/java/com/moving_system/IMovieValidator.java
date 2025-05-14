package com.moving_system;

/**
 * Interface for movie data validation
 * Following Single Responsibility Principle
 */
public interface IMovieValidator {
    boolean isValidId(int id);
    boolean isIdUnique(int id);
    boolean isValidTitle(String title);
    boolean isValidReleaseDate(String releaseDate);
    boolean isValidRevenue(long revenue);
    boolean isValidRuntime(double runtime);
    boolean isValidCompanie(String companie);
    boolean isValidGenre(String genre);
    boolean isValidProdCountry(String prodCountry);
    boolean isValidVoteAverage(double voteAverage);
}
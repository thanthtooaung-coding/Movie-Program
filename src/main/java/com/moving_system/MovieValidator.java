package com.moving_system;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Validation implementation for movies
 */
public class MovieValidator implements IMovieValidator {
    private final IMovieDao movieDao;

    public MovieValidator(IMovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public boolean isValidId(int id) {
        return id > 0;
    }

    @Override
    public boolean isIdUnique(int id) {
        return movieDao.getMovieById(id) == null;
    }

    @Override
    public boolean isValidTitle(String title) {
        return title != null && !title.trim().isEmpty();
    }

    @Override
    public boolean isValidReleaseDate(String releaseDate) {
        // Basic date format validation (YYYY-MM-DD)
        String regex = "\\d{4}-\\d{2}-\\d{2}";
        return releaseDate != null && Pattern.matches(regex, releaseDate);
    }

    @Override
    public boolean isValidRevenue(long revenue) {
        return revenue >= 0;
    }

    @Override
    public boolean isValidRuntime(double runtime) {
        return runtime > 0;
    }

    @Override
    public boolean isValidCompanie(String companie) {
        return companie != null && !companie.trim().isEmpty();
    }

    @Override
    public boolean isValidGenre(String genre) {
        return genre != null && !genre.trim().isEmpty();
    }

    @Override
    public boolean isValidProdCountry(String prodCountry) {
        return prodCountry != null && !prodCountry.trim().isEmpty();
    }

    @Override
    public boolean isValidVoteAverage(double voteAverage) {
        return voteAverage >= 0 && voteAverage <= 10;
    }
}

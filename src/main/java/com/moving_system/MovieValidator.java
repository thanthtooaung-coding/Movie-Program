package com.moving_system;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Singleton implementation of IMovieValidator
 */
public class MovieValidator implements IMovieValidator {
    private static MovieValidator instance;

    private final IMovieDao movieDao;

    public MovieValidator(IMovieDao movieDao) {
        this.movieDao = movieDao;
    }

    // Singleton accessor
    public static MovieValidator getInstance(IMovieDao movieDao) {
        if (instance == null) {
            instance = new MovieValidator(movieDao);
        }
        return instance;
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

    public boolean isValidReleaseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return false;
        }

        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        try {
            LocalDate.parse(dateStr, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public boolean isValidRevenue(long revenue) {
        return revenue >= 0;
    }

    @Override
    public boolean isValidRuntime(int runtime) {
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

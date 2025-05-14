import com.moving_system.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MovieValidatorTest {

    private MovieValidator validator;

    static class StubMovieDao implements IMovieDao {
        @Override
        public List<Movie> getAllMovies() {
            return java.util.Collections.emptyList();
        }

        @Override
        public Movie getMovieById(int id) {
            if (id == 2) {
                return new Movie(2, "Test", "1/1/2020", 0, 90, "Test", "Drama", "USA", 8.0);
            }
            return null;
        }

        @Override
        public boolean addMovie(Movie movie) {
            return true;
        }

        @Override
        public boolean updateMovie(Movie movie) {
            return true;
        }

        @Override
        public boolean deleteMovie(int id) {
            return true;
        }

        @Override
        public void saveToFile() {

        }
    }

    @BeforeEach
    void setUp() {
        validator = new MovieValidator(new StubMovieDao());
    }

    @Test
    void testValidId() {
        assertTrue(validator.isValidId(1));
        assertFalse(validator.isValidId(0));
        assertFalse(validator.isValidId(-5));
    }

    @Test
    void testIdUnique() {
        assertTrue(validator.isIdUnique(1));
        assertFalse(validator.isIdUnique(2));
    }

    @Test
    void testValidTitle() {
        assertTrue(validator.isValidTitle("Inception"));
        assertFalse(validator.isValidTitle(""));
        assertFalse(validator.isValidTitle("   "));
        assertFalse(validator.isValidTitle(null));
    }

    @Test
    void testValidReleaseDate() {
        assertTrue(validator.isValidReleaseDate("1/15/2020"));
        assertFalse(validator.isValidReleaseDate("2020/01/15"));
        assertFalse(validator.isValidReleaseDate(""));
        assertFalse(validator.isValidReleaseDate(null));
    }

    @Test
    void testValidRevenue() {
        assertTrue(validator.isValidRevenue(0));
        assertTrue(validator.isValidRevenue(1000000));
        assertFalse(validator.isValidRevenue(-1));
    }

    @Test
    void testValidRuntime() {
        assertTrue(validator.isValidRuntime(120));
        assertFalse(validator.isValidRuntime(0));
        assertFalse(validator.isValidRuntime(-100));
    }

    @Test
    void testValidCompanie() {
        assertTrue(validator.isValidCompanie("Warner Bros"));
        assertFalse(validator.isValidCompanie(""));
        assertFalse(validator.isValidCompanie("   "));
        assertFalse(validator.isValidCompanie(null));
    }

    @Test
    void testValidGenre() {
        assertTrue(validator.isValidGenre("Action"));
        assertFalse(validator.isValidGenre(""));
        assertFalse(validator.isValidGenre("   "));
        assertFalse(validator.isValidGenre(null));
    }

    @Test
    void testValidProdCountry() {
        assertTrue(validator.isValidProdCountry("USA"));
        assertFalse(validator.isValidProdCountry(""));
        assertFalse(validator.isValidProdCountry("   "));
        assertFalse(validator.isValidProdCountry(null));
    }

    @Test
    void testValidVoteAverage() {
        assertTrue(validator.isValidVoteAverage(7.5));
        assertTrue(validator.isValidVoteAverage(0.0));
        assertTrue(validator.isValidVoteAverage(10.0));
        assertFalse(validator.isValidVoteAverage(-0.1));
        assertFalse(validator.isValidVoteAverage(10.1));
    }
}

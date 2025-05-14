import com.moving_system.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

public class MovieActionTest {

    private IMovieDao movieDao;
    private IMovieValidator validator;

    @BeforeEach
    void setUp() {
        movieDao = mock(IMovieDao.class);
        validator = mock(IMovieValidator.class);
        resetSingletons();
    }

    @Test
    void testAddMovie_successfulAddition() {
        String input = String.join("\n",
                "1",        // ID
                "Movie 1",  // Title
                "05/10/2024", // Release Date
                "100000",   // Revenue
                "120",      // Runtime
                "Pixar",    // Company
                "Action",   // Genre
                "USA",      // Prod Country
                "8.5"       // Vote Average
        );
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(validator.isValidId(1)).thenReturn(true);
        when(validator.isIdUnique(1)).thenReturn(true);
        when(validator.isValidTitle("Movie 1")).thenReturn(true);
        when(validator.isValidReleaseDate("05/10/2024")).thenReturn(true);
        when(validator.isValidRevenue(100000L)).thenReturn(true);
        when(validator.isValidRuntime(120)).thenReturn(true);
        when(validator.isValidCompanie("Pixar")).thenReturn(true);
        when(validator.isValidGenre("Action")).thenReturn(true);
        when(validator.isValidProdCountry("USA")).thenReturn(true);
        when(validator.isValidVoteAverage(8.5)).thenReturn(true);
        when(movieDao.addMovie(any(Movie.class))).thenReturn(true);

        AddMovie.getInstance(movieDao, validator).execute();

        verify(movieDao).addMovie(any(Movie.class));
        verify(movieDao).saveToFile();
    }

    @Test
    void testDeleteMovie_userConfirmsDeletion() {
        String input = String.join("\n",
                "1",    // ID
                "y"     // Confirmation
        );
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Movie mockMovie = new Movie(1, "Test", "01/01/2020", 0, 90, "Pixar", "Action", "USA", 7.5);
        when(movieDao.getMovieById(1)).thenReturn(mockMovie);
        when(movieDao.deleteMovie(1)).thenReturn(true);

        DeleteMovie.getInstance(movieDao).execute();

        verify(movieDao).deleteMovie(1);
        verify(movieDao).saveToFile();
    }

    @Test
    void testDisplayAllMovies_emptyList() {
        when(movieDao.getAllMovies()).thenReturn(Collections.emptyList());

        DisplayAllMovies.getInstance(movieDao).execute();

        verify(movieDao).getAllMovies();
    }

    @Test
    void testUpdateMovie_successfulUpdate() {
        String input = String.join("\n",
                "1",        // Movie ID
                "y",        // Confirm update
                "Updated Title",     // New title
                "01/01/2023",        // New release date
                "500000",            // New revenue
                "130",               // New runtime
                "Marvel",            // New company
                "Adventure",         // New genre
                "Canada",            // New country
                "9.1"                // New vote average
        );
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Movie existingMovie = new Movie(1, "Old Title", "12/12/2020", 100000, 120, "Pixar", "Action", "USA", 8.0);
        when(movieDao.getMovieById(1)).thenReturn(existingMovie);

        when(validator.isValidId(1)).thenReturn(true);
        when(validator.isValidTitle("Updated Title")).thenReturn(true);
        when(validator.isValidReleaseDate("01/01/2023")).thenReturn(true);
        when(validator.isValidRevenue(500000L)).thenReturn(true);
        when(validator.isValidRuntime(130)).thenReturn(true);
        when(validator.isValidCompanie("Marvel")).thenReturn(true);
        when(validator.isValidGenre("Adventure")).thenReturn(true);
        when(validator.isValidProdCountry("Canada")).thenReturn(true);
        when(validator.isValidVoteAverage(9.1)).thenReturn(true);
        when(movieDao.updateMovie(any(Movie.class))).thenReturn(true);

        UpdateMovie.getInstance(movieDao, validator).execute();

        verify(movieDao).updateMovie(any(Movie.class));
        verify(movieDao).saveToFile();
    }

    @Test
    void testGenerateReports_successfullyGenerates() {
        Movie movie1 = new Movie(1, "A", "01/01/2020", 100000, 120, "Pixar", "Action", "USA", 7.5);
        Movie movie2 = new Movie(2, "B", "01/01/2021", 200000, 130, "Marvel", "Adventure", "UK", 8.2);

        when(movieDao.getAllMovies()).thenReturn(List.of(movie1, movie2));

        GenerateReports.getInstance(movieDao).execute();

        verify(movieDao).getAllMovies();
    }

    private void resetSingletons() {
        try {
            java.lang.reflect.Field field;

            field = AddMovie.class.getDeclaredField("instance");
            field.setAccessible(true);
            field.set(null, null);

            field = DeleteMovie.class.getDeclaredField("instance");
            field.setAccessible(true);
            field.set(null, null);

            field = DisplayAllMovies.class.getDeclaredField("instance");
            field.setAccessible(true);
            field.set(null, null);

            field = UpdateMovie.class.getDeclaredField("instance");
            field.setAccessible(true);
            field.set(null, null);

            field = GenerateReports.class.getDeclaredField("instance");
            field.setAccessible(true);
            field.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

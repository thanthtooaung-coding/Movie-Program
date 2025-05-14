import com.moving_system.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.Collections;

import static org.mockito.Mockito.*;

public class MovieServiceImplTest {

    private IMovieDao movieDao;
    private IMovieValidator validator;
    private MovieServiceImpl movieService;

    @BeforeEach
    void setUp() {
        movieDao = mock(IMovieDao.class);
        validator = mock(IMovieValidator.class);
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

        movieService = new MovieServiceImpl(movieDao, validator);
        movieService.addMovie();

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

        movieService = new MovieServiceImpl(movieDao, validator);
        movieService.deleteMovie();

        verify(movieDao).deleteMovie(1);
        verify(movieDao).saveToFile();
    }

    @Test
    void testDisplayAllMovies_emptyList() {
        when(movieDao.getAllMovies()).thenReturn(Collections.emptyList());

        movieService = new MovieServiceImpl(movieDao, validator);
        movieService.displayAllMovies();

        verify(movieDao).getAllMovies();
    }
}

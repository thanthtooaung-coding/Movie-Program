import com.moving_system.Movie;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MainTest {
    
    @Test
    public void testMovieCreation() {
        Movie movie = new Movie(1, "Test Movie", "2023-01-01", 1000000, 120.5,
                              "Test Studio", "Action", "USA", 7.5);
        
        assertEquals(1, movie.getId());
        assertEquals("Test Movie", movie.getTitle());
        assertEquals("2023-01-01", movie.getReleaseDate());
        assertEquals(1000000, movie.getRevenue());
        assertEquals(120.5, movie.getRuntime());
        assertEquals("Test Studio", movie.getCompanie());
        assertEquals("Action", movie.getGenre());
        assertEquals("USA", movie.getProdCountry());
        assertEquals(7.5, movie.getVoteAverage());
    }
    
    @Test
    public void testMovieToString() {
        Movie movie = new Movie(1, "Test Movie", "2023-01-01", 1000000, 120.5, 
                              "Test Studio", "Action", "USA", 7.5);
        
        String movieString = movie.toString();
        assertTrue(movieString.contains("id=1"));
        assertTrue(movieString.contains("title='Test Movie'"));
        assertTrue(movieString.contains("releaseDate='2023-01-01'"));
        assertTrue(movieString.contains("revenue=1000000"));
        assertTrue(movieString.contains("runtime=120.5"));
        assertTrue(movieString.contains("companie='Test Studio'"));
        assertTrue(movieString.contains("genre='Action'"));
        assertTrue(movieString.contains("prodCountry='USA'"));
        assertTrue(movieString.contains("voteAverage=7.5"));
    }
    
    @Test
    public void testMovieToCSV() {
        Movie movie = new Movie(1, "Test Movie", "2023-01-01", 1000000, 120.5, 
                              "Test Studio", "Action", "USA", 7.5);
        
        String csvString = movie.toCSV();
        assertEquals("1,Test Movie,2023-01-01,1000000,120.5,Test Studio,Action,USA,7.5", csvString);
    }
}
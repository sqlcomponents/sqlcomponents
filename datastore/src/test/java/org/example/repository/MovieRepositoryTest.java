package org.example.repository;

import org.example.model.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
class MovieRepositoryTest {

    private MovieRepository movieRepository;

    private Movie movie;

    @BeforeEach
    void setUp() {
        movie = new Movie((short) 1
                , "Inception"
                , "Christopher Nolan");
    }

    @Test
    @DisplayName("Test save and find movie by ID")
    void testSaveAndFindById() {
        // Save the movie
        Movie savedMovie = movieRepository.save(movie);
        assertNotNull(savedMovie);

        // Find the movie by ID
        Optional<Movie> retrievedMovie = movieRepository.findById(movie.id());
        assertTrue(retrievedMovie.isPresent());
        assertEquals(movie.title(), retrievedMovie.get().title());
        assertEquals(movie.directedBy(), retrievedMovie.get().directedBy());
    }

    @Test
    @DisplayName("Test update movie")
    void testUpdateMovie() {
        // Save the movie
        movieRepository.save(movie);

        // Update the title and save again
        movie = movie.withTitle("Interstellar");
        Movie updatedMovie = movieRepository.save(movie);
        assertEquals("Interstellar", updatedMovie.title());
    }

    @Test
    @DisplayName("Test delete movie by ID")
    void testDeleteById() {
        // Save the movie
        movieRepository.save(movie);

        // Delete the movie
        movieRepository.deleteById(movie.id());
        Optional<Movie> deletedMovie = movieRepository.findById(movie.id());
        assertFalse(deletedMovie.isPresent());
    }

    @Test
    @DisplayName("Test find all movies")
    void testFindAll() {
        // Save multiple movies
        Movie movie2 = new Movie((short) 2
                , "The Matrix"
                , "Wachowskis");

        movieRepository.save(movie);
        movieRepository.save(movie2);

        // Verify findAll
        Iterable<Movie> movies = movieRepository.findAll();
        assertNotNull(movies);
        assertTrue(movies.spliterator().getExactSizeIfKnown() >= 2);
    }
}

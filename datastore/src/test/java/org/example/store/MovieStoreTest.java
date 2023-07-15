package org.example.store;

import org.example.MovieManager;
import org.example.model.Movie;
import org.example.util.DataSourceProvider;
import org.example.util.EncryptionUtil;
import org.example.util.JsonUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.example.store.MovieStore.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MovieStoreTest {
    private final MovieStore movieStore;
    private List<Movie> moviesToTest;

    MovieStoreTest() {
        MovieManager movieManager = MovieManager.getManager(DataSourceProvider.dataSource(),
                EncryptionUtil::enAnDecrypt, EncryptionUtil::enAnDecrypt);
        // Stores used for testing
        this.movieStore = movieManager.getMovieStore();
    }

    @BeforeAll
    void init() throws SQLException {
        this.movieStore.delete().execute();
    }

    @Test
    void testFind() throws SQLException {
        Movie movie = new Movie();

        movie.setTitle("Avatar");
        movie.setDirectedBy("James Cameroon");
        movie.setGenre("Action");
        movie.setYearOfRelease((short) 2012);
        movie.setImdbId("SSSS");
        movie.setRating(3.5);

        Movie result = this.movieStore.insert().values(movie).returning();
        System.out.println(result);
    }


}
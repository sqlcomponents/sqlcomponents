package org.example.store;

import org.example.model.Movie;
import org.example.util.DataSourceProvider;
import org.example.util.JsonUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

class MovieStoreTest {

    private final MovieStore movieStore;
    private final List<Movie> moviesToTest;

    MovieStoreTest() {
        // Stores used for testing
        this.movieStore = new MovieStore(DataSourceProvider.dataSource());

        // Data used for testing
        this.moviesToTest = JsonUtil.getTestObjects(Movie.class);
    }

    @BeforeEach
    void init() throws SQLException {
        this.movieStore.deleteAll();
    }

    @Test
    void testFindWithUnique() throws SQLException {
        List<Movie> insertedMovies = this.movieStore.insert()
                .values(moviesToTest).returning();
        Assertions.assertEquals(moviesToTest.size(), this.movieStore.find(), "Loading Movies");
    }

    @Test
    void testWhereClause() throws SQLException {
        List<Movie> insertedMovies = this.movieStore.insert()
                .values(moviesToTest).returning();
        Assertions.assertEquals(moviesToTest.size(), insertedMovies.size(), "Loading Movies");
    }
}
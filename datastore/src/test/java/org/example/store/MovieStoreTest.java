package org.example.store;

import org.example.MovieManager;
import org.example.model.Movie;
import org.example.util.DataSourceProvider;
import org.example.util.JsonUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.example.store.MovieStore.title;
import static org.example.store.MovieStore.yearOfRelease;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MovieStoreTest {
    private final MovieStore movieStore;
    private List<Movie> moviesToTest;

    MovieStoreTest() {
        MovieManager movieManager = MovieManager.getManager(DataSourceProvider.dataSource());
        // Stores used for testing
        this.movieStore = movieManager.getMovieStore();
    }

    @BeforeAll
    void init() throws SQLException {
        this.movieStore.delete().execute();
        // Data used for testing
        this.moviesToTest = this.movieStore.insert().values(JsonUtil.getTestObjects(Movie.class)).returning();
    }

    @Test
    void testFind() throws SQLException {
        Optional<Movie> movie = this.movieStore
                .select(this.movieStore.select(title().eq("Memento")).execute().get(0).getId());
        Assertions.assertNotNull(movie.get().getCreatedAt(), "Insert Map Value is set");
        Assertions.assertNull(movie.get().getModifiedBy(), "Insert Map Non Value is not set");
        Assertions.assertNull(movie.get().getModifiedAt(), "Insert Map Non Value is not set");
        Assertions.assertEquals("Memento", movie.get().getTitle(), "Find By PK");
    }

    @Test
    void testUpdate() throws SQLException {
        Optional<Movie> movie = this.movieStore
                .select(this.movieStore.select(title().eq("Memento")).execute().get(0).getId());
        Short movieId = movie.get().getId();

        movie.get().setTitle("Update Title");
        this.movieStore.update(movie.get());
        movie = this.movieStore
                .select(movieId);

        Assertions.assertNotNull(movie.get().getModifiedAt(), "Update Map Non Value is not set");
        Assertions.assertEquals("Update Title", this.movieStore.select(movieId).get().getTitle(), "Update Failed");

    }

    @Test
    void testWhereClause() throws SQLException {
        Assertions.assertEquals(moviesToTest.size(), this.movieStore.select().execute().size(), "Select All");
    }

    @Test
    void testLimitClause() throws SQLException {
        Assertions.assertEquals(2, this.movieStore.select().limit(2).execute().getContent().size(),
                "Select With Limit");

        Assertions.assertEquals(2, this.movieStore.select().limit(2).offset(3).execute().getContent().size(),
                "Select With Limit");

        Assertions.assertEquals(moviesToTest.size(), this.movieStore.select().limit(2).execute().getTotalElements(),
                "Select All Count");
    }

    @Test
    void testWhereClauseSingleCriteria() throws SQLException {
        Assertions.assertEquals(1, this.movieStore.select(title().eq("Memento")).execute().size(),
                "Select All Single Criteria");
    }

    @Test
    void testWhereClauseMultipleANDCriteria() throws SQLException {
        Assertions.assertEquals(1, this.movieStore
                .select(yearOfRelease().eq((short) 2017).and().directedBy().eq("Christopher Nolan")).execute().size(),
                "Select All Single Criteria");
    }

    @Test
    void testWhereClauseMultipleORCriteria() throws SQLException {
        Assertions.assertEquals(12, this.movieStore
                .select(yearOfRelease().eq((short) 2017).or().directedBy().eq("Christopher Nolan")).execute().size(),
                "Select All Single Criteria");
    }
}
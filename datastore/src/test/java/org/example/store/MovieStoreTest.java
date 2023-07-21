package org.example.store;

import org.example.MovieManager;
import org.example.model.Movie;
import org.example.util.DataSourceProvider;
import org.example.util.EncryptionUtil;
import org.example.util.JsonUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
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
        movie = this.movieStore.select(movieId);

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

    @Test
    void testPartialUpdate() throws SQLException {
        int updatedRows = this.movieStore.update().set(directedBy("Sathish")).where(yearOfRelease().gt((short) 0))
                .execute();

        // Assertions.assertEquals(this.moviesToTest.size(), updatedRows, "Partial Update is not working");
    }

    /**
     * Test Plan. The returned object should be non-null The returned object should have the same values as the inserted
     * object. The returned object should have non-null id.
     *
     * @throws SQLException
     */
    @Test
    void testSingleReturning() throws SQLException {
        this.movieStore.delete().execute();

        Movie movieToInsert = moviesToTest.get(0);
        Movie insertedMovie = this.movieStore.insert().values(movieToInsert).returning();

        Assertions.assertNotNull(insertedMovie, "Inserted Movie is null");

        Assertions.assertNotNull(insertedMovie.getId(), "Inserted Movie id is null");

        checkEquality(movieToInsert, insertedMovie);
    }

    /**
     * Test Plan. The returned list length should be same as the inserted list length. The objects in the returned list
     * should have the same values as the inserted objects. The objects in the returned list should be in the same order
     * as the inserted objects. The objects in the returned list should have non-null id.
     *
     * @throws SQLException
     */
    @Test
    void testMultipleReturning() throws SQLException {
        this.movieStore.delete().execute();

        List<Movie> insertedMovies = this.movieStore.insert().values(moviesToTest).returning();

        Assertions.assertEquals(insertedMovies.size(), moviesToTest.size(), "Inserted Movies count is not same");

        for (int i = 0; i < insertedMovies.size(); i++) {
            Movie insertedMovie = insertedMovies.get(i);
            Movie movieToInsert = moviesToTest.get(i);

            Assertions.assertNotNull(insertedMovie, "Inserted Movie is null");

            Assertions.assertNotNull(insertedMovie.getId(), "Inserted Movie id is null");

            checkEquality(movieToInsert, insertedMovie);
        }
    }

    /**
     * Checks Equality of the Objects.
     *
     * @param movieToInsert
     * @param insertedMovie
     */
    private static void checkEquality(final Movie movieToInsert, final Movie insertedMovie) {
        Assertions.assertEquals(insertedMovie.getTitle(), movieToInsert.getTitle(), "Inserted Movie title is not same");
        Assertions.assertEquals(insertedMovie.getYearOfRelease(), movieToInsert.getYearOfRelease(),
                "Inserted Movie yearOfRelease is not same");
        Assertions.assertEquals(insertedMovie.getDirectedBy(), movieToInsert.getDirectedBy(),
                "Inserted Movie directedBy is not same");
        Assertions.assertEquals(insertedMovie.getImdbId(), movieToInsert.getImdbId(),
                "Inserted Movie imdbId is not same");
        Assertions.assertEquals(insertedMovie.getRating(), movieToInsert.getRating(),
                "Inserted Movie rating is not same");
        Assertions.assertEquals(insertedMovie.getGenre(), movieToInsert.getGenre(), "Inserted Movie genre is not same");
    }
}
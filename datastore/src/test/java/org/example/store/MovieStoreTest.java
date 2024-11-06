package org.example.store;

import org.example.DataManager;
import org.example.model.Movie;
import org.example.util.DataSourceProvider;
import org.example.util.EncryptionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

import static org.example.store.MovieStore.directedBy;
import static org.example.store.MovieStore.title;


class MovieStoreTest {

    private final DataSource dataSource;

    private final MovieStore movieStore;

    public MovieStoreTest() {

        this.dataSource = DataSourceProvider.dataSource();

        DataManager dataManager =
                DataManager.getManager(
                        EncryptionUtil::enAnDecrypt,
                        EncryptionUtil::enAnDecrypt);

        this.movieStore = dataManager.getMovieStore();
    }

    @BeforeEach
    void init() throws SQLException {
        this.movieStore.delete().execute(dataSource);
    }

    @Test
    void testBasic() throws SQLException {

        Movie movie = movieStore
                .insert()
                    .values(new Movie(null, "Inception", "Christopher Nolan"))
                .returning(dataSource);

        Assertions.assertTrue(movieStore.exists(dataSource,movie.id()));

        movie = movieStore
                .select(movie.id())
                .execute(dataSource);

        movie = movieStore
                .select(movie.id())
                    .where(directedBy().eq("Christopher Nolan"))
                .execute(dataSource);

        Assertions.assertNotNull(movie);

        List<Movie> movies = movieStore
                .insert()
                .values(new Movie(null, "Pulp Fiction", "Quentin Tarantino"),
                        new Movie(null, "The Matrix", "Lana Wachowski"),
                        new Movie(null, "Dunkirk", "Christopher Nolan"),
                        new Movie(null, "Fight Club", "David Fincher"),
                        new Movie(null, "Interstellar", "Christopher Nolan"),
                        new Movie(null, "The Social Network", "David Fincher"))
                .returning(dataSource);

        Assertions.assertEquals(6, movies.size());
        movie = movieStore
                .insert()
                    .values(new Movie(null, "The Dark Knight", "Christopher Nolan"))
                .returning(dataSource);

        movies = movieStore
                .select()
                .execute(dataSource);

        movies = movieStore
                .select()
                    .where(directedBy().eq("Christopher Nolan"))
                .execute(dataSource);

        movies = movieStore
                .select()
                .where(title().eq("Fight Club")).execute(dataSource);


        int updatedRecords = movieStore
                .update()
                        .set(new Movie(null, "Fight Club", "Martyn Scorsese"))
                .where(title().eq("Fight Club"))
                .execute(dataSource);

        Assertions.assertEquals(1, updatedRecords, "Update Failed");

        updatedRecords = movieStore
                .update()
                    .set(directedBy("Martyn Scorsese"))
                    .where(title().eq("Fight Club"))
                .execute(dataSource);

        Assertions.assertEquals(1, updatedRecords, "Update Failed");

        movies = movieStore
                .select()
                .where(title().eq("Fight Club")).execute(dataSource);

        Assertions.assertEquals("Martyn Scorsese", movies.get(0).directedBy());

        // Delete directed By "Martyn Scorsese"

        int updatedRows = this.movieStore
                .delete()
                    .where(directedBy().eq("Martyn Scorsese"))
                .execute(dataSource);

        Assertions.assertEquals(1, updatedRows);

    }
}
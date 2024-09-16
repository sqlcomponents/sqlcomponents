package org.example.store;

import org.example.DataManager;
import org.example.model.Movie;
import org.example.util.DataSourceProvider;
import org.example.util.EncryptionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.example.store.MovieStore.directedBy;
import static org.example.store.MovieStore.title;

@Disabled
class MovieStoreTest {

    private final MovieStore movieStore;

    public MovieStoreTest() {
        DataManager dataManager =
                DataManager.getManager(DataSourceProvider.dataSource(),
                        EncryptionUtil::enAnDecrypt,
                        EncryptionUtil::enAnDecrypt);

        this.movieStore = dataManager.getMovieStore();
    }

    @BeforeEach
    void init() throws SQLException {
        this.movieStore.delete().execute();
    }

    @Test
    void testBasic() throws SQLException {

        movieStore
                .insert()
                    .values(new Movie(null, "Inception", "Christopher Nolan"))
                .execute();

        movieStore
                .insert()
                    .values(new Movie(null, "Pulp Fiction", "Quentin Tarantino"))
                    .values(new Movie(null, "The Matrix", "Lana Wachowski"))
                    .values(new Movie(null, "Dunkirk", "Christopher Nolan"))
                    .values(new Movie(null, "Fight Club", "David Fincher"))
                    .values(new Movie(null, "Interstellar", "Christopher Nolan"))
                    .values(new Movie(null, "The Social Network", "David Fincher"))
                .execute();

        Movie movie = movieStore
                .insert()
                    .values(new Movie(null, "The Dark Knight", "Christopher Nolan"))
                .returning();

        List<Movie> movies = movieStore
                .select()
                .execute();

        movies = movieStore
                .select()
                    .where(directedBy().eq("Christopher Nolan"))
                .execute();

        movies = movieStore
                .select()
                .where(title().eq("Fight Club")).execute();

        Movie tobeUpdated = movies.get(0);
        tobeUpdated = tobeUpdated.withDirectedBy("Martyn Scorsese");
        int updatedRecords = movieStore
                .update()
                        .set(tobeUpdated)
                .where(title().eq("Fight Club"))
                .execute();


        Assertions.assertEquals(1, updatedRecords, "Update Failed");
        movies = movieStore
                .select()
                .where(title().eq("Fight Club")).execute();

        Assertions.assertEquals("Martyn Scorsese", movies.get(0).getDirectedBy());
    }
}
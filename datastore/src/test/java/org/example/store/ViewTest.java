package org.example.store;

import org.example.DataManager;
import org.example.model.Movie;
import org.example.model.MovieView;
import org.example.util.DataSourceProvider;
import org.example.util.EncryptionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.example.store.MovieViewStore.directedBy;
import static org.example.store.MovieViewStore.id;
import static org.example.store.MovieViewStore.title;

public class ViewStoreTest {
    private final MovieStore movieStore;
    private final MovieViewStore movieViewStore;

    public ViewStoreTest() {

        DataManager dataManager =
                DataManager.getManager(DataSourceProvider.dataSource(),
                        EncryptionUtil::enAnDecrypt,
                        EncryptionUtil::enAnDecrypt);
        this.movieViewStore = dataManager.getMovieViewStore();
        this.movieStore = dataManager.getMovieStore();
    }

    @BeforeEach
    void init() throws SQLException {
        this.movieStore.delete().execute();
    }

    public List<Movie> insertDataOnce() throws SQLException {
        return movieStore
                .insert()
                .values(new Movie(null, "Pulp Fiction", "Quentin Tarantino"),
                        new Movie(null, "The Matrix", "Lana Wachowski"),
                        new Movie(null, "Dunkirk", "Christopher Nolan"),
                        new Movie(null, "Fight Club", "David Fincher"),
                        new Movie(null, "Interstellar", "Christopher Nolan"),
                        new Movie(null, "Interstellar2", null),
                        new Movie(null, null, "Interstellar3"),
                        new Movie(Short.parseShort("23"), "New Data", "New one"),
                        new Movie(null, "The Soci   al Network", "David Fincher"))
                .returning();
    }

    @Test
    void testSelectBasic() throws SQLException {
        List<Movie> movies = insertDataOnce();
        List<MovieView> movieList = movieViewStore.select().execute();
        MovieView movieView = movieViewStore.select().where(title().eq("Fight Club")).execute().stream().findFirst().orElse(null);
        Assertions.assertNotNull(movieView);
        Assertions.assertEquals(movies.size(), movieList.size());
        Assertions.assertEquals("David Fincher", movieView.getDirectedBy());
    }

    @Test
    void testSelectWithWhereAndLimit() throws SQLException {
        int limitCount = 1;
        insertDataOnce();
        List<MovieView> movieList = movieViewStore.select().where(title().eq("Interstellar2")).limit(limitCount).execute().getContent();
        Assertions.assertNotNull(movieList);
        Assertions.assertEquals(limitCount, movieList.size());
    }

    @Test
    void testSelectWithLimit() throws SQLException {
        insertDataOnce();
        int limitCount = 4;
        int offsetCount = 2;
        List<MovieView> movieViews = movieViewStore.select().limit(limitCount).execute().getContent();
        List<MovieView> movieViewWithOffset = movieViewStore.select().limit(limitCount).offset(offsetCount).execute().getContent();
        Assertions.assertNotNull(movieViews);
        Assertions.assertEquals(limitCount, movieViews.size());
        Assertions.assertEquals("Quentin Tarantino", movieViews.get(0).getDirectedBy());
        Assertions.assertEquals("Christopher Nolan", movieViewWithOffset.get(0).getDirectedBy());
    }

    @Test
    void testSelectWithSql() throws SQLException {
        List<Movie> realMovie = insertDataOnce();
        Optional<MovieView> movieViews = this.movieViewStore
                .select().sql("SELECT id, title, directed_by FROM \"movie_view\" WHERE title = ?")
                .param(title("Fight Club"))
                .optional();
        List<MovieView> movieView = this.movieViewStore.select().where(directedBy().isNull()).execute();
        List<MovieView> movieViewNotNull = this.movieViewStore.select().where(directedBy().isNotNull()).execute();

        Assertions.assertEquals("Interstellar2", movieView.get(0).getTitle());
        Assertions.assertEquals(realMovie.size() - movieView.size(), movieViewNotNull.size());
        Assertions.assertTrue(movieViews.isPresent());
        Assertions.assertEquals("David Fincher", movieViews.get().getDirectedBy());
    }

    @Test
    void testListMovie() throws SQLException {
        List<Movie> realMovie = insertDataOnce();
        List<MovieView> movieViews = movieViewStore.select().sql("SELECT id, title, directed_by FROM \"movie_view\"").list();
        Assertions.assertEquals(realMovie.size(), movieViews.size());
    }

    @Test
    void testAndORWhereClause() throws SQLException {
        insertDataOnce();
        List<MovieView> movieViews = movieViewStore.select().where(id().eq(Short.parseShort("23")).and(title().isNotNull()).or(title().eq("New Data"))).execute();
        List<MovieView> movieViewsNull = movieViewStore.select().where(title().isNull()).execute();
        Assertions.assertEquals("Interstellar3", movieViewsNull.get(0).getDirectedBy());
        Assertions.assertNotNull(movieViews.get(0).getTitle());
        Assertions.assertEquals("New one", movieViews.get(0).getDirectedBy());
    }

    @Test
    void testIdNotNull() throws SQLException {
        Assertions.assertEquals(insertDataOnce().size(), movieViewStore.select().where(id().isNotNull()).execute().size());
    }

}

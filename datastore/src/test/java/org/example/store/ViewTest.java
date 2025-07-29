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

class ViewTest {
    private final DataSource dataSource;
    private final MovieStore movieStore;
    private final MovieViewStore movieViewStore;
    private final MaterializedMovieViewStore materializedMovieViewStore;

    public ViewTest() {
        this.dataSource = DataSourceProvider.dataSource();
        DataManager dataManager =
                DataManager.getManager(
                        EncryptionUtil::enAnDecrypt,
                        EncryptionUtil::enAnDecrypt);
        this.movieViewStore = dataManager.getMovieViewStore();
        this.movieStore = dataManager.getMovieStore();
        this.materializedMovieViewStore = dataManager
               .getMaterializedMovieViewStore();
    }

    @BeforeEach
    void init() throws SQLException {
        this.movieStore.delete().execute(dataSource);
        this.materializedMovieViewStore.refresh(dataSource);
        this.movieStore
                .insert()
                .values(new Movie(null, "Pulp Fiction", "Quentin Tarantino"),
                        new Movie(null, "The Matrix", "Lana Wachowski"))
                .execute(dataSource);
    }

    @Test
    void testViews() throws SQLException {
        Assertions.assertEquals(2, this.movieViewStore.select().execute(dataSource).size());

        // No Data as View is not refreshed
        Assertions.assertEquals(0, this.materializedMovieViewStore.select().execute(dataSource).size());
        // Refresh the Materialized View
        this.materializedMovieViewStore.refresh(dataSource);
        // Data as View is now refreshed
        Assertions.assertEquals(2, this.materializedMovieViewStore.select().execute(dataSource).size());

    }

}

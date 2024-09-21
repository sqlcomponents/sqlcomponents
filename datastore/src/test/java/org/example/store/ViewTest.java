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

class ViewTest {
    private final MovieStore movieStore;
    private final MovieViewStore movieViewStore;
  //  private final MaterializedMovieViewStore materializedMovieViewStore;

    public ViewTest() {
        DataManager dataManager =
                DataManager.getManager(DataSourceProvider.dataSource(),
                        EncryptionUtil::enAnDecrypt,
                        EncryptionUtil::enAnDecrypt);
        this.movieViewStore = dataManager.getMovieViewStore();
        this.movieStore = dataManager.getMovieStore();
     //   this.materializedMovieViewStore = dataManager
     //          .getMaterializedMovieViewStore();
    }

    @BeforeEach
    void init() throws SQLException {
        this.movieStore.delete().execute();
        this.movieStore
                .insert()
                .values(new Movie(null, "Pulp Fiction", "Quentin Tarantino"),
                        new Movie(null, "The Matrix", "Lana Wachowski"))
                .execute();
    }

    @Test
    void testViews() throws SQLException {
        Assertions.assertEquals(2, this.movieViewStore.select().execute().size());

        // No Data as View is not refreshed
     //   Assertions.assertEquals(0, this.materializedMovieViewStore.select().execute().size());
        // Refresh the Materialized View
      //  this.materializedMovieViewStore.refresh();
        // Data as View is now refreshed
      //  Assertions.assertEquals(0, this.materializedMovieViewStore.select().execute().size());

    }

}

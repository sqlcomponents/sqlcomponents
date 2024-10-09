package org.example.store;

import org.example.DataManager;
import org.example.model.Movie;
import org.example.util.DataSourceProvider;
import org.example.util.EncryptionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.example.store.MovieStore.*;

class CountTest {

    private final MovieStore movieStore;

    CountTest() {

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
    void testSql() throws SQLException {

        // Single Primary Key
        this.movieStore.insert().values(new Movie(null,"Vettayan","Gyanavel")).execute();
        Assertions.assertEquals(1, this.movieStore.select().count());

        // Multiple Primary Keys

        // No Primary Key (Code Table)


    }
}
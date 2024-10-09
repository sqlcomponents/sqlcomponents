package org.example.store;

import org.example.DataManager;
import org.example.model.Cache;
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
    private final CacheStore cacheStore;

    CountTest() {

        DataManager dataManager =
                DataManager.getManager(DataSourceProvider.dataSource(),
                        EncryptionUtil::enAnDecrypt,
                        EncryptionUtil::enAnDecrypt);

        this.movieStore = dataManager.getMovieStore();
        this.cacheStore = dataManager.getCacheStore();
    }


    @BeforeEach
    void init() throws SQLException {
        this.movieStore.delete().execute();
        this.cacheStore.delete().execute();
    }

    @Test
    void testSql() throws SQLException {

        // No Primary Key (Code Table)
        this.cacheStore.insert().values(new Cache("a","b")).execute();
        Assertions.assertEquals(1, this.cacheStore.select().count());

        // Single Primary Key
        this.movieStore.insert().values(new Movie(null,"Vettayan","Gyanavel")).execute();
        Assertions.assertEquals(1, this.movieStore.select().count());

        // Multiple Primary Keys




    }
}
package org.example;

import org.example.model.Movie;
import org.example.store.MovieStore;
import org.example.util.DataSourceProvider;
import org.example.util.EncryptionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.SQLException;

class SqlBuilderTest {
    private final DataSource dataSource;
    private final MovieStore movieStore;

    SqlBuilderTest() {

        this.dataSource = DataSourceProvider.dataSource();

        DataManager dataManager =
                DataManager.getManager(dataSource,
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

        Movie movie = movieStore
                .insert()
                .values(new Movie(null, "Inception", "Christopher Nolan"))
                .returning();

        movie = DataManager.SqlBuilder.sql("SELECT id,title,directed_by FROM MOVIE where id= ?")
                .param(movie.id())
                .query((rs, rowNum) -> new Movie(rs.getShort(1),
                                                    rs.getString(2),
                                                    rs.getString(3)
                ))
                .single(dataSource.getConnection());

        Assertions.assertEquals("Inception", movie.title());
    }
}
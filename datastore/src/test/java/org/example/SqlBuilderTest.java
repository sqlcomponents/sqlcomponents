package org.example;

import org.example.model.Movie;
import org.example.store.MovieStore;
import org.example.util.DataSourceProvider;
import org.example.util.EncryptionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.example.store.MovieStore.*;

class SqlBuilderTest {
    private final DataManager dataManager ;
    private final DataSource dataSource;
    private final MovieStore movieStore;

    SqlBuilderTest() {

        this.dataSource = DataSourceProvider.dataSource();

        this.dataManager =
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

        try(Connection connection = dataSource.getConnection()) {
            dataManager.sql("INSERT INTO movie ( title ,directed_by ) VALUES ( ? ,? )")
                    .param(title("Inception"))
                    .param(directedBy("Christopher Nolan"))
                    .prepare()
                    .executeUpdate(connection);

            Movie movie = dataManager.sql("SELECT id,title,directed_by FROM MOVIE where id= ?")
                    .param(id(this.movieStore.select().execute().get(0).id()))
                    .query((rs) -> new Movie(rs.getShort(1),
                            rs.getString(2),
                            rs.getString(3)
                    ))
                    .single(dataSource.getConnection());

            Assertions.assertEquals("Inception", movie.title());
        }


    }

    @Test
    void testTransaction() throws SQLException {

        Assertions.assertThrows(SQLException.class, () -> {
            dataManager
                    .begin()
                    .perform(dataManager.sql("INSERT INTO movie ( title ,directed_by ) VALUES ( ? ,? )")
                            .param(title("Inception"))
                            .param(directedBy("Christopher Nolan"))
                            .prepare())
                    // Invalid Insert. Should Fail.
                    .perform(dataManager.sql("INSERT INTO movie ( title ,directed_by ) VALUES ( NULL ,? )")
                            .param(directedBy("Christopher Nolan"))
                            .prepare())
                    .commit();
        });

        Assertions.assertEquals(0, this.movieStore.select().count());

    }
}
package org.example;

import org.example.store.ActorStore;
import static org.example.store.ActorStore.actorId;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class Client {

    public static void main(String[] args) throws SQLException {
        ActorStore actorStore = new ActorStore(dataSource());

//        actorStore.select(actorId().eq(100L))
//        .forEach(actor-> System.out.println(actor.getFirstName()));

        System.out.println(actorStore.exists(1L));
    }


















































    private static DataSource dataSource() {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setServerNames(new String[]{"localhost"});
        ds.setUser("moviedb");
        ds.setPassword("moviedb");
        return ds;
    }
}

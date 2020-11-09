package org.example;

import org.example.store.AllInAllAzaguRajaStore;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class Client {

    public static void main(String[] args) throws SQLException {
        AllInAllAzaguRajaStore allInAllAzaguRajaStore = new AllInAllAzaguRajaStore(dataSource());

//        actorStore.select(actorId().eq(100L))
//        .forEach(actor-> System.out.println(actor.getFirstName()));

        System.out.println(allInAllAzaguRajaStore.exists(1L,1L));
    }


















































    private static DataSource dataSource() {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setServerNames(new String[]{"localhost"});
        ds.setUser("moviedb");
        ds.setPassword("moviedb");
        return ds;
    }
}

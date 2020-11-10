package org.example;

import org.example.model.AllInAllAzaguRaja;
import org.example.store.AllInAllAzaguRajaStore;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.SQLException;

public class Client {

    public static void main(String[] args) throws SQLException {
        AllInAllAzaguRajaStore allInAllAzaguRajaStore = new AllInAllAzaguRajaStore(dataSource());

//        actorStore.select(actorId().eq(100L))
//        .forEach(actor-> System.out.println(actor.getFirstName()));

        AllInAllAzaguRaja allInAllAzaguRaja = new AllInAllAzaguRaja();
        allInAllAzaguRaja.setCode(1L);
        allInAllAzaguRaja.setTitle("Title");
        allInAllAzaguRajaStore.insert(allInAllAzaguRaja);
        System.out.println(allInAllAzaguRajaStore.find(1L));
    }


















































    private static DataSource dataSource() {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setServerNames(new String[]{"localhost"});
        ds.setUser("moviedb");
        ds.setPassword("moviedb");
        return ds;
    }
}

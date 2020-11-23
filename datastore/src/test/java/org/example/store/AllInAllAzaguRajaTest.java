package org.example.store;

import org.example.model.AllInAllAzaguRaja;
import org.example.model.AllInAllAzaguRajaReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.SQLException;
import java.util.List;

import static org.example.util.DataSourceProvider.dataSource;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AllInAllAzaguRajaTest {

    private final AllInAllAzaguRajaReferenceStore allAzaguRajaReferenceStore;
    private final AllInAllAzaguRajaStore allInAllAzaguRajaStore;


    AllInAllAzaguRajaTest() {
        this.allAzaguRajaReferenceStore = new AllInAllAzaguRajaReferenceStore(dataSource());
        this.allInAllAzaguRajaStore = new AllInAllAzaguRajaStore(dataSource());
    }



    @Test
    void testAllInAllAzaguRaja() throws SQLException {
//        this.allInAllAzaguRajaStore.deleteAll();
//        this.allAzaguRajaReferenceStore.deleteAll();

        List<AllInAllAzaguRaja> allinallazagurajaList = this.allInAllAzaguRajaStore.select();
        Assertions.assertEquals(1, allinallazagurajaList.size(), "list of allinallazaghuraja");

    }
}
package org.example.store;

import org.example.model.AllInAllAzaguRaja;
import org.example.model.AllInAllAzaguRajaReference;
import org.junit.jupiter.api.*;

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

    @BeforeEach
    void init() throws SQLException {
        this.allInAllAzaguRajaStore.deleteAll();
        this.allAzaguRajaReferenceStore.deleteAll();
    }

    @Test
    void testAllInAllAzaguRaja() throws SQLException {

        AllInAllAzaguRajaReference azagurajaobject = new AllInAllAzaguRajaReference();
        azagurajaobject.setCode("A110");
        azagurajaobject.setName("Hari");
        this.allAzaguRajaReferenceStore.insert(azagurajaobject);

        //this is to check mymodel, --> insert by setting parameter.
        AllInAllAzaguRaja allInAllAzaguRaja = new AllInAllAzaguRaja();
        allInAllAzaguRaja.setReferenceCode("A110");
        AllInAllAzaguRaja insertedAllInAllAzaguRaja = this.allInAllAzaguRajaStore.insert()
                .value(allInAllAzaguRaja).returning();


        Assertions.assertEquals("A110", insertedAllInAllAzaguRaja.getReferenceCode(), "found successfully");

    }
}
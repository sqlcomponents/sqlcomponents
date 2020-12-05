package org.example.store;

import org.example.model.AzaguRaja;
import org.example.model.AzaguRajaReference;
import org.junit.jupiter.api.*;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import static org.example.util.DataSourceProvider.dataSource;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AzaguRajaTest {

    private final AzaguRajaReferenceStore azaguRajaReferenceStore;
    private final AzaguRajaStore allInAllAzaguRajaStore;

    AzaguRajaTest() {
        this.azaguRajaReferenceStore = new AzaguRajaReferenceStore(dataSource());
        this.allInAllAzaguRajaStore = new AzaguRajaStore(dataSource());
    }

    @BeforeEach
    void init() throws SQLException {
        this.allInAllAzaguRajaStore.deleteAll();
        this.azaguRajaReferenceStore.deleteAll();
    }

    @Test
    void testAzaguRaja() throws SQLException {

        AzaguRajaReference azagurajaobject = new AzaguRajaReference();
        azagurajaobject.setCode("A110");
        azagurajaobject.setName("Hari");

        Integer noOfInsertedRajaRefs = this.azaguRajaReferenceStore.insert().values(azagurajaobject).execute();
        Assertions.assertEquals(1, noOfInsertedRajaRefs, "1 Raja Reference inserted");

        //this is to check mymodel, --> insert by setting parameter.
        AzaguRaja azaguRaja = new AzaguRaja();
        azaguRaja.setReferenceCode("A110");
        azaguRaja.setABoolean(true);
        azaguRaja.setAChar('A');
        azaguRaja.setAText("Text");

        AzaguRaja insertedAzaguRaja = this.allInAllAzaguRajaStore.insert()
                .values(azaguRaja).returning();

        Assertions.assertEquals("A110", insertedAzaguRaja.getReferenceCode(), "found successfully");


    }
}
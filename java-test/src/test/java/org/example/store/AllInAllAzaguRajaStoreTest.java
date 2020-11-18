package org.example.store;

import static org.example.util.DataSourceProvider.dataSource;

import org.example.model.AllInAllAzaguRaja;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AllInAllAzaguRajaStoreTest {

    private final AllInAllAzaguRajaStore allInAllAzaguRajaStore;

    private AllInAllAzaguRaja allInAllAzaguRaja;

    AllInAllAzaguRajaStoreTest() {
        this.allInAllAzaguRajaStore = new AllInAllAzaguRajaStore(dataSource());
    }

    @BeforeAll
    void init() throws SQLException {
        allInAllAzaguRajaStore.delete(1);
        allInAllAzaguRaja = new AllInAllAzaguRaja();
        allInAllAzaguRaja.setA3DigitNumber(2);
        allInAllAzaguRajaStore.insert().value(allInAllAzaguRaja).returning();
    }

    @Test
    void create() throws SQLException {
        assertNotNull(allInAllAzaguRaja,"Azagu Raja created");
    }
}
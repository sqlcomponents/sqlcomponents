package org.example.store;

import static org.example.util.DataSourceProvider.dataSource;

import org.example.model.AllInAllAzaguRaja;
import org.example.model.AllInAllAzaguRajaReference;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AllInAllAzaguRajaReferenceStoreTest {

    private final AllInAllAzaguRajaStore allInAllAzaguRajaStore;
    
    
    private AllInAllAzaguRaja allInAllAzaguRaja;
    private AllInAllAzaguRajaReference allInAllAzaguRajaReference;
    
    
    AllInAllAzaguRajaReferenceStoreTest() {
        this.allInAllAzaguRajaStore = new AllInAllAzaguRajaStore(dataSource());
    }

    @BeforeAll
    void init() throws SQLException {
        allInAllAzaguRajaStore.deleteAll();
        allInAllAzaguRaja = new AllInAllAzaguRaja();
        allInAllAzaguRaja.setA7DigitNumber(3);
//        allInAllAzaguRaja.setVarchar("4");
        allInAllAzaguRaja = allInAllAzaguRajaStore.insert()
                .value(allInAllAzaguRaja).returning();

    }

    @Test
    void create() throws SQLException {
        assertNotNull(allInAllAzaguRajaStore.find(allInAllAzaguRaja.getCode()),"Azagu Raja created");
    }
}
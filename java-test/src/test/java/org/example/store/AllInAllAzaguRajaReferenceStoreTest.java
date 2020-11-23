package org.example.store;

import static org.example.util.DataSourceProvider.dataSource;

import org.example.model.AllInAllAzaguRajaReference;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AllInAllAzaguRajaReferenceStoreTest {

    private final AllInAllAzaguRajaReferenceStore allAzaguRajaReferenceStore;

    private AllInAllAzaguRajaReference allInAllAzaguRajaReference;
    
    
    AllInAllAzaguRajaReferenceStoreTest() {
        this.allAzaguRajaReferenceStore = new AllInAllAzaguRajaReferenceStore(dataSource());
    }

    @BeforeAll
    void init() throws SQLException {


    }

    @Test
    void create() throws SQLException {
           }
}
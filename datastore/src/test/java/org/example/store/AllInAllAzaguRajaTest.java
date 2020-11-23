package org.example.store;

import org.example.model.AllInAllAzaguRajaReference;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.SQLException;

import static org.example.util.DataSourceProvider.dataSource;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AllInAllAzaguRajaTest {

    private final AllInAllAzaguRajaReferenceStore allAzaguRajaReferenceStore;

    AllInAllAzaguRajaTest() {
        this.allAzaguRajaReferenceStore = new AllInAllAzaguRajaReferenceStore(dataSource());
    }

    @BeforeAll
    void init() throws SQLException {

    }

    @Test
    void testAllInAllAzaguRaja() throws SQLException {

    }
}
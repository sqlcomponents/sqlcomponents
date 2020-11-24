package org.example.store;

import org.example.model.AllInAllAzaguRajaReference;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static org.example.util.DataSourceProvider.dataSource;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AllInAllAzaguRajaTest {

    private final AllInAllAzaguRajaReferenceStore allAzaguRajaReferenceStore;

    AllInAllAzaguRajaTest() {
        this.allAzaguRajaReferenceStore = new AllInAllAzaguRajaReferenceStore(dataSource());
    }

    @BeforeEach
    void init() throws SQLException {
        this.allAzaguRajaReferenceStore.deleteAll();
    }

    @Test
    void testAllInAllAzaguRaja() throws SQLException {
        AllInAllAzaguRajaReference a = new AllInAllAzaguRajaReference();
        AllInAllAzaguRajaReference b = new AllInAllAzaguRajaReference();
        a.setCode("1");
        a.setName("x");
        b.setCode("2");
        b.setName("y");
        this.allAzaguRajaReferenceStore.insert(a);
        this.allAzaguRajaReferenceStore.insert(b);
        Assertions.assertNotNull(a);
        a.setName("34");
        this.allAzaguRajaReferenceStore.update(a);
        Assertions.assertEquals("34", this.allAzaguRajaReferenceStore.find("1").getName(), "success");
    }

}
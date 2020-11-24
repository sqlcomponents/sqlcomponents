package org.example.store;

import org.example.model.AllInAllAzaguRaja;
import org.example.model.AllInAllAzaguRajaReference;
<<<<<<< HEAD
<<<<<<< HEAD
import org.junit.jupiter.api.*;
=======
=======
>>>>>>> 99720a355689c54690ae6e8094e91cfcfdc0cf00
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
>>>>>>> 99720a355689c54690ae6e8094e91cfcfdc0cf00

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

<<<<<<< HEAD
<<<<<<< HEAD
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
=======


    @Test
    void testAllInAllAzaguRaja() throws SQLException {
//        this.allInAllAzaguRajaStore.deleteAll();
//        this.allAzaguRajaReferenceStore.deleteAll();
=======


    @Test
    void testAllInAllAzaguRaja() throws SQLException {
//        this.allInAllAzaguRajaStore.deleteAll();
//        this.allAzaguRajaReferenceStore.deleteAll();

        List<AllInAllAzaguRaja> allinallazagurajaList = this.allInAllAzaguRajaStore.select();
        Assertions.assertEquals(1, allinallazagurajaList.size(), "list of allinallazaghuraja");
>>>>>>> 99720a355689c54690ae6e8094e91cfcfdc0cf00

        List<AllInAllAzaguRaja> allinallazagurajaList = this.allInAllAzaguRajaStore.select();
        Assertions.assertEquals(1, allinallazagurajaList.size(), "list of allinallazaghuraja");

>>>>>>> 99720a355689c54690ae6e8094e91cfcfdc0cf00
    }

}
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


        //This is to check reference,--> insert by setting parameters.
        AllInAllAzaguRajaReference azagurajaobject = new AllInAllAzaguRajaReference();
        azagurajaobject.setCode("A110");
        azagurajaobject.setName("Hari");
        this.allAzaguRajaReferenceStore.insert(azagurajaobject);

        //this is to check mymodel, --> insert by setting parameter.
        AllInAllAzaguRaja allInAllAzaguRaja = new AllInAllAzaguRaja();
        allInAllAzaguRaja.setReferenceCode("A110");
        Integer returnedId = this.allInAllAzaguRajaStore.insertAndGet(allInAllAzaguRaja);

        //this is to check reference, --> find with a given code.
        AllInAllAzaguRajaReference insertedObject = this.allAzaguRajaReferenceStore.find("A110");

        //this is to check mymodel, --> find with a given id.
        AllInAllAzaguRaja insertedAllinallazaguraja = this.allInAllAzaguRajaStore.find(returnedId);
        Assertions.assertEquals("A110", insertedAllinallazaguraja.getReferenceCode(), "found successfully");
//
//        //this is to check reference, --> update by updating a parameter.
//        insertedObject.setName("Priya");
//        this.allAzaguRajaReferenceStore.update(insertedObject);
//        Assertions.assertEquals("Priya", this.allAzaguRajaReferenceStore.find("A110").getName(), "reference updated successfully");

        //List<AllInAllAzaguRaja> allinallazagurajaList = this.allInAllAzaguRajaStore.select();
        //Assertions.assertEquals(1, allinallazagurajaList.size(), "list of allinallazaghuraja");
    }
}
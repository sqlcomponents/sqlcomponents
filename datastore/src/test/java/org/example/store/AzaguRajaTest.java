package org.example.store;


import org.example.model.AzaguRaja;
import org.example.model.AzaguRajaReference;
import org.junit.jupiter.api.*;


import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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

        List<AzaguRajaReference> list = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            azagurajaobject = new AzaguRajaReference();
            azagurajaobject.setCode("A11"+i);
            azagurajaobject.setName("Hari"+i);
            list.add(azagurajaobject);
        }
        int[] noOfInsertedRajaRefsArray = this.azaguRajaReferenceStore.insert().values(list).execute();
        Assertions.assertEquals(5, noOfInsertedRajaRefsArray.length, "5 Raja Reference inserted");


        AzaguRaja azaguRaja = new AzaguRaja();
        azaguRaja.setReferenceCode("A110");
        azaguRaja.setABoolean(true);
        // azaguRaja.setAChar('A');
        azaguRaja.setAText("Text");
        azaguRaja.setADate(LocalDate.now());
        azaguRaja.setATime(LocalTime.now());


        AzaguRaja insertedAzaguRaja = this.allInAllAzaguRajaStore.insert()
                .values(azaguRaja).returning();

        Assertions.assertEquals("A110", insertedAzaguRaja.getReferenceCode(), "found successfully");


    }
}
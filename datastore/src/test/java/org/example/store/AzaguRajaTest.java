package org.example.store;


import org.example.model.AzaguRaja;
import org.example.model.AzaguRajaReference;
import org.example.util.JsonUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.SQLException;
import java.time.LocalDate;
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
        String databaseType = "postgres";

        List<AzaguRajaReference> azaguRajaReferencesToTest = JsonUtil.getAzaguRajaReferences(databaseType);

        Integer noOfInsertedRajaRefs = this.azaguRajaReferenceStore.insert().values(azaguRajaReferencesToTest.get(0)).execute();
        Assertions.assertEquals(1, noOfInsertedRajaRefs, "1 Raja Reference not inserted");


        azaguRajaReferenceStore.deleteAll();

        int[] noOfInsertedRajaRefsArray = this.azaguRajaReferenceStore.insert().values(azaguRajaReferencesToTest).execute();
        Assertions.assertEquals(5, noOfInsertedRajaRefsArray.length, "All Raja Reference not inserted");


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


        List<AzaguRaja> listOfRajas = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            listOfRajas.add(azaguRaja);
        }

        List<AzaguRaja> insertedAzaguRajas = this.allInAllAzaguRajaStore.insert()
                .values(listOfRajas).returning();

        Assertions.assertEquals(listOfRajas.size(), insertedAzaguRajas.size(), "all raja found successfully");


    }
}
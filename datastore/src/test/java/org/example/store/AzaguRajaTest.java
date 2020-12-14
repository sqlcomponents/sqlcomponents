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
import static org.example.store.AzaguRajaStore.referenceCode;

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


        List<AzaguRajaReference> azaguRajaReferencesToTest = JsonUtil.getTestObjects(AzaguRajaReference.class);

        List<AzaguRaja> azaguRajasToTest = JsonUtil.getTestObjects(AzaguRaja.class);

        AzaguRaja azaguRaja = azaguRajasToTest.get(0);
        azaguRaja.setASmallint((short) 3);
        azaguRaja.setABigint(9L);
        azaguRaja.setADecimal(88L);
        azaguRaja.setADouble(9.00);
        azaguRaja.setANumeric(8L);
        azaguRaja.setAReal(1.2F);
        azaguRaja.setAInteger(8L);

        System.out.println(JsonUtil.getJSONString(azaguRaja));
        Integer noOfInsertedRajaRefs = this.azaguRajaReferenceStore.insert().values(azaguRajaReferencesToTest.get(0)).execute();
        Assertions.assertEquals(1, noOfInsertedRajaRefs, "1 Raja Reference not inserted");


        azaguRajaReferenceStore.deleteAll();

        int[] noOfInsertedRajaRefsArray = this.azaguRajaReferenceStore.insert().values(azaguRajaReferencesToTest).execute();
        Assertions.assertEquals(5, noOfInsertedRajaRefsArray.length, "All Raja Reference not inserted");

        AzaguRaja insertedAzaguRaja = this.allInAllAzaguRajaStore.insert()
                .values(azaguRajasToTest.get(0)).returning();

        Assertions.assertEquals(azaguRajasToTest.get(0).getReferenceCode(), insertedAzaguRaja.getReferenceCode(), "found successfully");


        this.allInAllAzaguRajaStore.deleteAll();

        List<AzaguRaja> insertedAzaguRajas = this.allInAllAzaguRajaStore.insert()
                .values(azaguRajasToTest).returning();

        Assertions.assertEquals(azaguRajasToTest.size(), insertedAzaguRajas.size(), "all raja found successfully");

        List<AzaguRaja> selectedAzaguRajas = this.allInAllAzaguRajaStore.select(referenceCode().eq("A110"));
        Assertions.assertEquals(azaguRajasToTest.size(), selectedAzaguRajas.size(), "all raja found successfully");

    }
}
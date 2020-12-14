package org.example.store;

import org.example.model.AzaguRaja;
import org.example.model.AzaguRajaReference;
import org.example.util.DataSourceProvider;
import org.example.util.JsonUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.SQLException;
import java.util.List;


import static org.example.store.AzaguRajaStore.referenceCode;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AzaguRajaTest {

    private final AzaguRajaReferenceStore azaguRajaReferenceStore;
    private final AzaguRajaStore allInAllAzaguRajaStore;


    AzaguRajaTest() {
        this.azaguRajaReferenceStore = new AzaguRajaReferenceStore(DataSourceProvider.dataSource());
        this.allInAllAzaguRajaStore = new AzaguRajaStore(DataSourceProvider.dataSource());
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
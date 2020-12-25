package org.example.store;

import org.example.MovieManager;
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

/**
 * Azaguraja
 * 1. Reference for all the data types.
 * 2. All Persistance Intefaces.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AzaguRajaTest {

    private final AzaguRajaReferenceStore azaguRajaReferenceStore;
    private final AzaguRajaStore allInAllAzaguRajaStore;

    private final List<AzaguRajaReference> azaguRajaReferencesToTest;
    private final List<AzaguRaja> azaguRajasToTest;


    AzaguRajaTest() {
        MovieManager movieManager = MovieManager.getManager(DataSourceProvider.dataSource());
        // Stores used for testing
        this.azaguRajaReferenceStore = movieManager.getAzaguRajaReferenceStore();
        this.allInAllAzaguRajaStore = movieManager.getAzaguRajaStore();

        // Data used for testing
        this.azaguRajaReferencesToTest = JsonUtil.getTestObjects(AzaguRajaReference.class);
        this.azaguRajasToTest = JsonUtil.getTestObjects(AzaguRaja.class);
    }

    @BeforeEach
    void init() throws SQLException {
        // Clean Up
        this.allInAllAzaguRajaStore.deleteAll();
        this.azaguRajaReferenceStore.deleteAll();
    }

    @Test
    void testSingleInsertAndGetNumberOfRows() throws SQLException {
        Integer noOfInsertedRajaRefs = this.azaguRajaReferenceStore.
                insert()
                .values(azaguRajaReferencesToTest.get(0))
                .execute();
        Assertions.assertEquals(1, noOfInsertedRajaRefs, "Single Insert Execution");
    }

    @Test
    void testMultipleInsertAndGetNumberOfRows() throws SQLException {
        int[] noOfInsertedRajaRefsArray = this.azaguRajaReferenceStore
                .insert()
                .values(azaguRajaReferencesToTest)
                .execute();
        Assertions.assertEquals(5, noOfInsertedRajaRefsArray.length, "Multiple Insert Execution");
    }

    @Test
    void testSingleInsertAndGetInsertedObject() throws SQLException {

        AzaguRajaReference azaguRajaReference = this.azaguRajaReferenceStore
                .insert()
                .values(azaguRajaReferencesToTest.get(0))
                .returning();

        AzaguRaja azaguRaja = azaguRajasToTest.get(0);
        azaguRaja.setReferenceCode(azaguRajaReference.getCode());

        AzaguRaja insertedAzaguRaja = this.allInAllAzaguRajaStore
                .insert()
                .values(azaguRaja)
                .returning();
        Assertions.assertEquals(azaguRaja.getReferenceCode()
                , insertedAzaguRaja.getReferenceCode()
                , "Single Insert Returning");
    }


    @Test
    void testMultiInsertAndGetInsertedObjects() throws SQLException {
        this.azaguRajaReferenceStore
                .insert()
                .values(azaguRajaReferencesToTest)
                .execute();

        List<AzaguRaja> insertedAzaguRajas = this.allInAllAzaguRajaStore
                .insert()
                .values(azaguRajasToTest)
                .returning();
        Assertions.assertEquals(azaguRajasToTest.size(), insertedAzaguRajas.size(), "Multi Insert Returning");
    }

    @Test
    void testMultiSequenceInsertAndGetInsertedObjects() throws SQLException {
        this.azaguRajaReferenceStore
                .insert()
                .values(azaguRajaReferencesToTest)
                .execute();

        List<AzaguRaja> insertedAzaguRajas = this.allInAllAzaguRajaStore
                .insert()
                .values(azaguRajasToTest.get(0))
                .values(azaguRajasToTest.get(1))
                .values(azaguRajasToTest.get(2))
                .returning();
        Assertions.assertEquals(3, insertedAzaguRajas.size(), "Multi Sequence Insert Returning");
    }


    @Test
    void testSingleUpdateAndGetNumberOfRows() throws SQLException {
        AzaguRajaReference azaguRajaReference = this.azaguRajaReferenceStore
        .insert().values(this.azaguRajaReferencesToTest.get(0)).returning();
        azaguRajaReference.setName("Changed");
        Integer noOfUpdatedRajaRefs
                = this.azaguRajaReferenceStore
                .update()
                .set(azaguRajaReference)
                .execute();
        Assertions.assertEquals(1, noOfUpdatedRajaRefs, "Single Update Execution");
    }


}
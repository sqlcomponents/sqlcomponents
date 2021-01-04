package org.example.store;

import org.example.MovieManager;
import org.example.model.AzaguRaja;
import org.example.model.Connection;
import org.example.util.DataSourceProvider;
import org.example.util.JsonUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Azaguraja
 * 1. Reference for all the data types.
 * 2. All Persistance Intefaces.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AzaguRajaTest {

    private final ConnectionStore connectionStore;
    private final AzaguRajaStore allInAllAzaguRajaStore;

    private final List<Connection> connectionsToTest;
    private final List<AzaguRaja> azaguRajasToTest;


    AzaguRajaTest() {
        MovieManager movieManager = MovieManager.getManager(DataSourceProvider.dataSource());
        // Stores used for testing
        this.connectionStore = movieManager.getConnectionStore();
        this.allInAllAzaguRajaStore = movieManager.getAzaguRajaStore();

        // Data used for testing
        this.connectionsToTest = JsonUtil.getTestObjects(Connection.class);
        this.azaguRajasToTest = JsonUtil.getTestObjects(AzaguRaja.class);

        this.azaguRajasToTest.parallelStream().forEach(azaguRaja -> {
            // Declare and initialize the byte array
            byte[] bb = { 10, 20, 30 };
            azaguRaja.setABlob(ByteBuffer.wrap(bb));
        });

    }

    @BeforeEach
    void init() throws SQLException {
        // Clean Up
        this.allInAllAzaguRajaStore.deleteAll();
        this.connectionStore.deleteAll();
    }

    @Test
    void testSingleInsertAndGetNumberOfRows() throws SQLException {
        Integer noOfInsertedRajaRefs = this.connectionStore.
                insert()
                .values(connectionsToTest.get(0))
                .execute();
        Assertions.assertEquals(1, noOfInsertedRajaRefs, "Single Insert Execution");
    }

    @Test
    void testMultipleInsertAndGetNumberOfRows() throws SQLException {
        int[] noOfInsertedRajaRefsArray = this.connectionStore
                .insert()
                .values(connectionsToTest)
                .execute();
        Assertions.assertEquals(5, noOfInsertedRajaRefsArray.length, "Multiple Insert Execution");
    }

    @Test
    void testSingleInsertAndGetInsertedObject() throws SQLException {

        Connection connection = this.connectionStore
                .insert()
                .values(connectionsToTest.get(0))
                .returning();

        AzaguRaja azaguRaja = azaguRajasToTest.get(0);
        azaguRaja.setReferenceCode(connection.getCode());

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
        this.connectionStore
                .insert()
                .values(connectionsToTest)
                .execute();

        List<AzaguRaja> insertedAzaguRajas = this.allInAllAzaguRajaStore
                .insert()
                .values(azaguRajasToTest)
                .returning();
        Assertions.assertEquals(azaguRajasToTest.size(), insertedAzaguRajas.size(), "Multi Insert Returning");
    }

    @Test
    void testMultiSequenceInsertAndGetInsertedObjects() throws SQLException {
        this.connectionStore
                .insert()
                .values(connectionsToTest)
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
        Connection connection = this.connectionStore
        .insert().values(this.connectionsToTest.get(0)).returning();
        connection.setName("Changed");
        Integer noOfUpdatedRajaRefs
                = this.connectionStore
                .update()
                .set(connection)
                .execute();
        Assertions.assertEquals(1, noOfUpdatedRajaRefs, "Single Update Execution");
    }


}
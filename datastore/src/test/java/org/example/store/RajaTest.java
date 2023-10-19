package org.example.store;

import org.example.RajaManager;
import org.example.model.Raja;
import org.example.model.Connection;
import org.example.util.DataSourceProvider;
import org.example.util.EncryptionUtil;
import org.example.util.JsonUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Azaguraja 1. Reference for all the data types. 2. All Persistence
 * Interfaces.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RajaTest {
    private final ConnectionStore connectionStore;
    private final RajaStore allInAllRajaStore;

    private final List<Connection> connectionsToTest;
    private final List<Raja> azaguRajasToTest;

    RajaTest() {
        RajaManager movieManager =
                RajaManager.getManager(DataSourceProvider.dataSource(),
                        EncryptionUtil::enAnDecrypt,
                        EncryptionUtil::enAnDecrypt);
        // Stores used for testing
        this.connectionStore = movieManager.getConnectionStore();
        this.allInAllRajaStore = movieManager.getRajaStore();

        // Data used for testing
        this.connectionsToTest = JsonUtil.getTestObjects(Connection.class);
        this.azaguRajasToTest = JsonUtil.getTestObjects(Raja.class);

        this.azaguRajasToTest.parallelStream().forEach(azaguRaja -> {
            // Declare and initialize the byte array
            byte[] bb = {10, 20, 30};
            azaguRaja.setABlob(ByteBuffer.wrap(bb));

            azaguRaja.setAUuid(UUID.randomUUID());

        });

    }

    @BeforeEach
    void init() throws SQLException {
        // Clean Up
        this.allInAllRajaStore.delete().execute();
        this.connectionStore.delete().execute();
    }

    @Test
    void testSelectWithWhere() throws SQLException {
        Connection connection = connectionsToTest.get(0);

        this.connectionStore.insert().values(connection).execute();

        Assertions.assertFalse(this.connectionStore.select(connection.getCode(),
                        ConnectionStore.name().isNull()).isPresent(),
                "Get Unique Value Execution");

        Assertions.assertTrue(this.connectionStore.select(connection.getCode(),
                        ConnectionStore.name().isNotNull()).isPresent(),
                "Get Unique Value Execution");

        Assertions.assertFalse(this.connectionStore.select(connection.getCode(),
                        ConnectionStore.name().eq(new Date().toString())).isPresent(),
                "Get Unique Value Execution");

        Assertions.assertTrue(this.connectionStore.select(connection.getCode(),
                        ConnectionStore.name().eq(connection.getName())).isPresent(),
                "Get Unique Value Execution");
    }

    @Test
    void testSingleInsertAndGetNumberOfRows() throws SQLException {
        Integer noOfInsertedRajaRefs =
                this.connectionStore.insert().values(connectionsToTest.get(0))
                        .execute();
        Assertions.assertEquals(1, noOfInsertedRajaRefs,
                "Single Insert Execution");
    }

    @Test
    void testUniqueGet() throws SQLException {
        Connection connection = connectionsToTest.get(0);
        Integer noOfInsertedRajaRefs =
                this.connectionStore.insert().values(connection).execute();

        Optional<Connection> connection2 =
                this.connectionStore.selectByName(connection.getName());

        Assertions.assertEquals(connection.getCode(),
                connection2.get().getCode(), "Get Unique Value Execution");
    }

    @Test
    void testExists() throws SQLException {
        Connection connection = connectionsToTest.get(0);
        Integer noOfInsertedRajaRefs =
                this.connectionStore.insert().values(connection).execute();

        Assertions.assertTrue(this.connectionStore.exists(connection.getCode()),
                "Exists by PK");
        Assertions.assertTrue(
                this.connectionStore.existsByName(connection.getName()),
                "Exists by Unique Key");
    }

    @Test
    void testMultipleInsertAndGetNumberOfRows() throws SQLException {
        int[] noOfInsertedRajaRefsArray =
                this.connectionStore.insert().values(connectionsToTest)
                        .execute();
        Assertions.assertEquals(5, noOfInsertedRajaRefsArray.length,
                "Multiple Insert Execution");
    }

    @Test
    void testSingleInsertAndGetInsertedObject() throws SQLException {

        Connection connection =
                this.connectionStore.insert().values(connectionsToTest.get(0))
                        .returning();

        Raja azaguRaja = azaguRajasToTest.get(0);
        azaguRaja.setReferenceCode(connection.getCode());

        Raja insertedRaja =
                this.allInAllRajaStore.insert().values(azaguRaja)
                        .returning();
        Assertions.assertEquals(azaguRaja.getReferenceCode(),
                insertedRaja.getReferenceCode(),
                "Single Insert Returning");
    }

    @Test
    void testMultiInsertAndGetInsertedObjects() throws SQLException {
        this.connectionStore.insert().values(connectionsToTest).execute();

        List<Raja> insertedRajas =
                this.allInAllRajaStore.insert().values(azaguRajasToTest)
                        .returning();
        Assertions.assertEquals(azaguRajasToTest.size(),
                insertedRajas.size(), "Multi Insert Returning");
    }

    @Test
    void testMultiSequenceInsertAndGetInsertedObjects() throws SQLException {
        this.connectionStore.insert().values(connectionsToTest).execute();

        List<Raja> insertedRajas =
                this.allInAllRajaStore.insert()
                        .values(azaguRajasToTest.get(0))
                        .values(azaguRajasToTest.get(1))
                        .values(azaguRajasToTest.get(2)).returning();
        Assertions.assertEquals(3, insertedRajas.size(),
                "Multi Sequence Insert Returning");
    }

    @Test
    void testTableFilterMaps() throws SQLException {
        this.connectionStore.insert().values(connectionsToTest).execute();

        Raja insertedRaja = this.allInAllRajaStore.insert()
                .values(azaguRajasToTest.get(0)).returning();
        Assertions.assertEquals(4, insertedRaja.getAInteger(),
                "Insert Map with Table and Column");
        // this.allInAllRajaStore.update(insertedRaja);
        // insertedRaja = this.allInAllRajaStore.select
        // (insertedRaja.getId()).get();
        // Assertions.assertEquals(5, insertedRaja.getAInteger(),
        // "Insert Map with Table and Column");

    }

    @Test
    void testEncryption() throws SQLException {
        this.connectionStore.insert().values(connectionsToTest).execute();

        azaguRajasToTest.get(0).setAEncryptedText("AEncryptedText");

        Raja insertedRaja = this.allInAllRajaStore.insert()
                .values(azaguRajasToTest.get(0)).returning();
        Assertions.assertEquals("AEncryptedText",
                insertedRaja.getAEncryptedText(),
                "Insert Map with Table and Column");

    }

    @Test
    void testDeleteWhereClause() throws SQLException {
        this.connectionStore.insert().values(connectionsToTest).execute();

        List<Raja> insertedRajas =
                this.allInAllRajaStore.insert()
                        .values(azaguRajasToTest.get(0))
                        .values(azaguRajasToTest.get(1))
                        .values(azaguRajasToTest.get(2)).returning();

        RajaStore.WhereClause whereClause =
                RajaStore.aBoolean().eq(true);
        int deletedRows =
                this.allInAllRajaStore.delete(whereClause).execute();

        Assertions.assertEquals(azaguRajasToTest.size() - deletedRows,
                this.allInAllRajaStore.select(whereClause).count(),
                "Multi Delete Where Clause");

        Assertions.assertEquals(1,
                this.connectionStore.delete(connectionsToTest.get(0).getCode()),
                "Delete By Id");
    }

    @Test
    void testSingleUpdateAndGetNumberOfRows() throws SQLException {
        Connection connection = this.connectionStore.insert()
                .values(this.connectionsToTest.get(0)).returning();
        connection.setName("Changed");
        Integer noOfUpdatedRajaRefs = this.connectionStore.update(connection);
        Assertions.assertEquals(1, noOfUpdatedRajaRefs,
                "Single Update Execution");
    }

    @Test
    void testSingleValueUpdateWithWhere() throws SQLException {
        Connection connection = this.connectionStore.insert()
                .values(this.connectionsToTest.get(0)).returning();
        final String originalName = connection.getName();

        Assertions.assertEquals(1, this.connectionStore
                        .update()
                        .set(ConnectionStore.name("Changed"))
                        .where(ConnectionStore.name().eq(originalName))
                        .execute(),
                "Single Update Execution");

    }

    @Test
    void testSingleUpdateWithWhere() throws SQLException {
        Connection connection = this.connectionStore.insert()
                .values(this.connectionsToTest.get(0)).returning();

        final String originalName = connection.getName();

        connection.setName("Changed");

        Assertions.assertEquals(0, this.connectionStore
                        .update()
                        .set(connection)
                        .where(ConnectionStore.name().eq(originalName+"SOMETHINGELSE"))
                        .execute(),
                "Single Update Execution");

        Assertions.assertEquals(1, this.connectionStore
                        .update()
                        .set(connection)
                        .where(ConnectionStore.name().eq(originalName))
                        .execute(),
                "Single Update Execution");
    }

}
package org.example.storedprocedure;

import org.example.DatabaseManager;
import org.example.model.Cache;
import org.example.store.CacheStore;
import org.example.util.DataSourceProvider;
import org.example.util.EncryptionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

class StoredProcedureTest {

    private final DatabaseManager databaseManager;
    private final CacheStore cacheStore;

    StoredProcedureTest() {
        databaseManager =
                DatabaseManager.getManager(DataSourceProvider.dataSource(),
                        EncryptionUtil::enAnDecrypt,
                        EncryptionUtil::enAnDecrypt);
        this.cacheStore = databaseManager.getCacheStore();
    }

    @BeforeEach
    void init() throws SQLException {
        // Clean Up
        this.cacheStore.delete().execute();
    }

    @Test
    void basicCall() throws SQLException {
        databaseManager.call().createCache("Name", "Raja");
        CacheStore.WhereClause whereClause= CacheStore.code().eq("Name");
        List<Cache> cacheList = databaseManager.getCacheStore().select().where(whereClause).execute();
        Assertions.assertEquals(1, cacheList.size());
        Assertions.assertEquals(cacheList.get(0).getCode(),"Name");
        Assertions.assertEquals(cacheList.get(0).getCache(),"Raja");
    }

    @Test
    void addFunction() throws SQLException {
        Byte result = null;
        databaseManager.call().add((byte)1,(byte)3, result);
        Assertions.assertNotNull(result);
        Assertions.assertEquals((byte)4, result);
    }


}

package org.example.storedprocedure;

import org.checkerframework.checker.units.qual.C;
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

    private final DatabaseManager rajaManager;
    private final CacheStore cacheStore;

    StoredProcedureTest() {
        rajaManager =
                DatabaseManager.getManager(DataSourceProvider.dataSource(),
                        EncryptionUtil::enAnDecrypt,
                        EncryptionUtil::enAnDecrypt);
        this.cacheStore = rajaManager.getCacheStore();
    }

    @BeforeEach
    void init() throws SQLException {
        // Clean Up
        this.cacheStore.delete().execute();
    }

    @Test
    void basicCall() throws SQLException {
        rajaManager.call().createCache("Name", "Raja");
        CacheStore.WhereClause whereClause= CacheStore.code().eq("Name");
        List<Cache> cacheList = rajaManager.getCacheStore().select(whereClause).execute();
        Assertions.assertEquals(1, cacheList.size());
        Assertions.assertEquals(cacheList.get(0).getCode(),"Name");
        Assertions.assertEquals(cacheList.get(0).getCache(),"Raja");
    }


}

package org.example.storedprocedure;

import org.example.DataManager;
import org.example.model.Accounts;
import org.example.model.Cache;
import org.example.store.AccountsStore;
import org.example.store.CacheStore;
import org.example.util.DataSourceProvider;
import org.example.util.EncryptionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

class StoredProcedureTest {
    private static final double BALANCE_DELTA = 0.001;
    private static final double SEED_BALANCE = 10_000.0;

    private final DataSource dataSource;
    private final DataManager dataManager;
    private final CacheStore cacheStore;
    private final AccountsStore accountsStore;

    StoredProcedureTest() {
        this.dataSource = DataSourceProvider.dataSource();
        dataManager = DataManager.getManager(EncryptionUtil::enAnDecrypt, EncryptionUtil::enAnDecrypt);
        this.cacheStore = dataManager.getCacheStore();
        this.accountsStore = dataManager.getAccountsStore();
    }


    @BeforeEach
    void init() throws SQLException {
        this.cacheStore.delete().execute(dataSource);
        resetSeedAccounts();
    }

    /**
     * Restores Raju / Nikhil rows used by {@code procedures.sql} seed data.
     */
    private void resetSeedAccounts() throws SQLException {
        accountsStore.update()
                .set(AccountsStore.balance(SEED_BALANCE))
                .where(AccountsStore.id().eq(1L))
                .execute(dataSource);
        accountsStore.update()
                .set(AccountsStore.balance(SEED_BALANCE))
                .where(AccountsStore.id().eq(2L))
                .execute(dataSource);
    }

    @Test
    void basicCall() throws SQLException {
        dataManager.call().createCache(dataSource, "Name", "Raja");
        CacheStore.WhereClause whereClause = CacheStore.code().eq("Name");
        List<Cache> cacheList = dataManager.getCacheStore().select().where(whereClause).execute(dataSource);
        Assertions.assertEquals(1, cacheList.size());
        Assertions.assertEquals(cacheList.get(0).code(), "Name");
        Assertions.assertEquals(cacheList.get(0).cache(), "Raja");
    }

    @Test
    void createCache_insertsMultipleRows() throws SQLException {
        dataManager.call().createCache(dataSource, "alpha", "first");
        dataManager.call().createCache(dataSource, "beta", "second");

        List<Cache> rows = cacheStore.select()
                .where(CacheStore.code().eq("alpha").or(CacheStore.code().eq("beta")))
                .execute(dataSource);

        Assertions.assertEquals(2, rows.size());
        Cache alpha = rows.stream().filter(c -> "alpha".equals(c.code())).findFirst().orElseThrow();
        Cache beta = rows.stream().filter(c -> "beta".equals(c.code())).findFirst().orElseThrow();
        Assertions.assertEquals("first", alpha.cache());
        Assertions.assertEquals("second", beta.cache());
    }

    @Test
    void addFunction() throws SQLException {
        Byte sum = dataManager.call().add(dataSource, (byte) 1, (byte) 3);
        Assertions.assertNotNull(sum);
        Assertions.assertEquals((byte) 4, sum.byteValue());
    }

    @Test
    void addFunction_zeroOperands() throws SQLException {
        Byte sum = dataManager.call().add(dataSource, (byte) 0, (byte) 0);
        Assertions.assertNotNull(sum);
        Assertions.assertEquals((byte) 0, sum.byteValue());
    }

    @Test
    void addFunction_largerOperands() throws SQLException {
        Byte sum = dataManager.call().add(dataSource, (byte) 40, (byte) 50);
        Assertions.assertNotNull(sum);
        Assertions.assertEquals((byte) 90, sum.byteValue());
    }

//    @Test
//    void transfer_updatesBothBalances() throws SQLException {
//        Accounts senderBefore = accountsStore.select(dataSource, 1L).orElseThrow();
//        Accounts receiverBefore = accountsStore.select(dataSource, 2L).orElseThrow();
//        double amount = 100.0;
//
//        dataManager.call().transfer(dataSource, (byte) 1, (byte) 2, (byte) amount);
//
//        Accounts senderAfter = accountsStore.select(dataSource, 1L).orElseThrow();
//        Accounts receiverAfter = accountsStore.select(dataSource, 2L).orElseThrow();
//
//        Assertions.assertEquals(senderBefore.balance() - amount, senderAfter.balance(), BALANCE_DELTA);
//        Assertions.assertEquals(receiverBefore.balance() + amount, receiverAfter.balance(), BALANCE_DELTA);
//    }

//    @Test
//    void transfer_isIdempotentWhenReSeeded() throws SQLException {
//        dataManager.call().transfer(dataSource, (byte) 1, (byte) 2, (byte) 50);
//        Assertions.assertEquals(SEED_BALANCE - 50,
//                accountsStore.select(dataSource, 1L).orElseThrow().balance(), BALANCE_DELTA);
//
//        resetSeedAccounts();
//
//        dataManager.call().transfer(dataSource, (byte) 2, (byte) 1, (byte) 25);
//        Assertions.assertEquals(SEED_BALANCE - 25,
//                accountsStore.select(dataSource, 2L).orElseThrow().balance(), BALANCE_DELTA);
//        Assertions.assertEquals(SEED_BALANCE + 25,
//                accountsStore.select(dataSource, 1L).orElseThrow().balance(), BALANCE_DELTA);
//    }
}

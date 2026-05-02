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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

/**
 * Integration tests for routines compiled into {@link org.example.DataManager.Procedure}
 * (see {@code compiler/src/main/resources/template/java/Procedures.ftl}).
 * <p>
 * <b>Architecture (generated API)</b>
 * <ul>
 *   <li>Entry point: {@code DataManager.getManager(...).call().&lt;method&gt;(dataSource, ...)}.</li>
 *   <li><b>IN only</b> (no result columns): {@code void} method; body uses {@code SqlBuilder.prepareCall}.</li>
 *   <li><b>PostgreSQL scalar function</b> (single return value, JDBC metadata ordinal {@code 0} for the
 *       return column): {@code {? = call name(?,...)} } and a Java return type.</li>
 *   <li><b>Exactly one OUT/INOUT</b> that is <em>not</em> a PG function return (ordinal {@code >= 1}):
 *       {@code CallableStatement} with one Java return type; JDBC indices follow
 *       {@link java.sql.DatabaseMetaData#getProcedureColumns} / {@code getFunctionColumns} ordinals.</li>
 *   <li><b>Multiple OUT/INOUT</b> (or mixed with IN): {@code void} and each output is a
 *       single-element array argument (element {@code [0]} is filled after execute).</li>
 * </ul>
 * <p>
 * <b>SQL coverage</b> (see {@code init.db/postgres/procedures.sql}; apply DDL then regenerate sources):
 * IN-only procedures, scalar SQL functions, procedures with one or more OUT parameters, INOUT-only procedure,
 * OUT-only procedure, and multi-IN scalar functions.
 * <p>
 * <b>Note on “VARDICT” / VARIADIC</b>: PostgreSQL {@code VARIADIC} is typically surfaced in JDBC metadata as a
 * single array-typed parameter; full coverage depends on array type mapping in the compiler. This suite
 * documents that contract; scalar and non-array signatures are asserted here.
 * <p>
 * Tests run in a <b>single thread</b> so one shared Hikari pool is not exhausted when JUnit
 * schedules nested and parameterized methods concurrently. {@code @BeforeEach} is scoped per
 * nested class (cache truncate vs account reseed) to limit connection churn.
 */
@Execution(ExecutionMode.SAME_THREAD)
class StoredProcedureTest {

    private static final double BALANCE_DELTA = 0.001;
    private static final double SEED_BALANCE = 10_000.0;

    /**
     * One pool for the whole class: JUnit constructs a new outer instance per {@code @Nested} class,
     * so a per-instance {@code DataSourceProvider.dataSource()} would open many pools and exhaust
     * PostgreSQL {@code max_connections}.
     */
    private static final DataSource DATA_SOURCE = DataSourceProvider.dataSource();

    private final DataSource dataSource = DATA_SOURCE;
    private final DataManager dataManager =
            DataManager.getManager(EncryptionUtil::enAnDecrypt, EncryptionUtil::enAnDecrypt);
    private final CacheStore cacheStore = dataManager.getCacheStore();
    private final AccountsStore accountsStore = dataManager.getAccountsStore();

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

    private static int byteOrInt(final Number n) {
        return n.intValue();
    }

    /**
     * <b>IN only</b>: parameters are inputs; no registered OUT columns — {@code void} on {@code Procedure}.
     */
    @Nested
    @DisplayName("IN-only procedures (void call)")
    class InOnly {

        @BeforeEach
        void clearCacheTable() throws SQLException {
            cacheStore.delete().execute(dataSource);
        }

        @Test
        @DisplayName("create_cache: two IN parameters, inserts one row")
        void basicCall() throws SQLException {
            dataManager.call().createCache(dataSource, "Name", "Raja");
            CacheStore.WhereClause whereClause = CacheStore.code().eq("Name");
            List<Cache> cacheList = dataManager.getCacheStore().select().where(whereClause).execute(dataSource);
            Assertions.assertEquals(1, cacheList.size());
            Assertions.assertEquals(cacheList.get(0).code(), "Name");
            Assertions.assertEquals(cacheList.get(0).cache(), "Raja");
        }

        @Test
        @DisplayName("create_cache: repeated calls — multiple IN invocations")
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
        @DisplayName("create_cache: empty string values (IN TEXT still valid)")
        void createCache_emptyStrings() throws SQLException {
            dataManager.call().createCache(dataSource, "", "");
            List<Cache> rows = cacheStore.select().where(CacheStore.code().eq("")).execute(dataSource);
            Assertions.assertEquals(1, rows.size());
            Assertions.assertEquals("", rows.get(0).cache());
        }

        @Test
        @DisplayName("create_cache: same code inserted twice (no PK on cache — two rows)")
        void createCache_duplicateCodeAllowed() throws SQLException {
            dataManager.call().createCache(dataSource, "dup", "v1");
            dataManager.call().createCache(dataSource, "dup", "v2");
            long dupCount = cacheStore.select()
                    .where(CacheStore.code().eq("dup"))
                    .execute(dataSource)
                    .size();
            Assertions.assertEquals(2, dupCount);
        }

        @Test
        @DisplayName("create_cache: non-ASCII IN parameters")
        void createCache_unicode() throws SQLException {
            dataManager.call().createCache(dataSource, "café", "日本語");
            List<Cache> rows = cacheStore.select().where(CacheStore.code().eq("café")).execute(dataSource);
            Assertions.assertEquals(1, rows.size());
            Assertions.assertEquals("日本語", rows.get(0).cache());
        }
    }

    /**
     * <b>Scalar SQL function</b>: PostgreSQL {@code RETURNS scalar} — one metadata return column (ordinal 0);
     * generated code uses {@code {? = call ...}} and returns the Java value (same path as {@code add}).
     */
    @Nested
    @DisplayName("PostgreSQL scalar functions (single return)")
    class ScalarFunctions {

        @Test
        @DisplayName("add: two IN, integer return")
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

        @Test
        @DisplayName("add: negative operands (wraps in byte range)")
        void addFunction_negativeOperands() throws SQLException {
            Byte sum = dataManager.call().add(dataSource, (byte) -5, (byte) 2);
            Assertions.assertNotNull(sum);
            Assertions.assertEquals((byte) -3, sum.byteValue());
        }

        @Test
        @DisplayName("add: commutative property")
        void addFunction_commutative() throws SQLException {
            Byte a = dataManager.call().add(dataSource, (byte) 7, (byte) 11);
            Byte b = dataManager.call().add(dataSource, (byte) 11, (byte) 7);
            Assertions.assertEquals(a, b);
        }

        @Test
        @DisplayName("fn_sum_three: three IN parameters, scalar return (IN + IN + IN)")
        void fnSum_threeInts() throws SQLException {
            Byte sum = dataManager.call().fnSumThree(dataSource, (byte) 2, (byte) 3, (byte) 4);
            Assertions.assertNotNull(sum);
            Assertions.assertEquals((byte) 9, sum.byteValue());
        }

        @Test
        @DisplayName("fn_sum_three: all zero IN")
        void fnSum_threeZeros() throws SQLException {
            Byte sum = dataManager.call().fnSumThree(dataSource, (byte) 0, (byte) 0, (byte) 0);
            Assertions.assertEquals((byte) 0, sum.byteValue());
        }

        @Test
        @DisplayName("fn_sum_three: negative IN values")
        void fnSum_threeWithNegatives() throws SQLException {
            Byte sum = dataManager.call().fnSumThree(dataSource, (byte) 10, (byte) -3, (byte) -2);
            Assertions.assertEquals((byte) 5, sum.byteValue());
        }

        @Test
        @DisplayName("fn_sum_three: maximum small operands still within byte")
        void fnSum_threeNearByteRange() throws SQLException {
            Byte sum = dataManager.call().fnSumThree(dataSource, (byte) 40, (byte) 41, (byte) 42);
            Assertions.assertEquals((byte) 123, sum.byteValue());
        }
    }

    /**
     * <b>IN + OUT</b>: one or more OUT columns at JDBC ordinals; single OUT returns from the method,
     * multiple OUTs use single-element output arrays.
     */
    @Nested
    @DisplayName("IN / OUT combinations")
    class InOutCombinations {

        @Test
        @DisplayName("sp_echo_len: IN text + single OUT int → Java return value")
        void spEchoLen_inPlusSingleOut() throws SQLException {
            Byte len = dataManager.call().spEchoLen(dataSource, "abcde");
            Assertions.assertNotNull(len);
            Assertions.assertEquals(5, byteOrInt(len));
        }

        @Test
        @DisplayName("sp_echo_len: empty string → length 0")
        void spEchoLen_emptyString() throws SQLException {
            Byte len = dataManager.call().spEchoLen(dataSource, "");
            Assertions.assertEquals(0, byteOrInt(len));
        }

        @Test
        @DisplayName("sp_echo_len: multi-byte Unicode length (code points, not UTF-16 units)")
        void spEchoLen_unicodeLength() throws SQLException {
            Byte len = dataManager.call().spEchoLen(dataSource, "日本"); // 2 characters
            Assertions.assertEquals(2, byteOrInt(len));
        }

        @Test
        @DisplayName("sp_divmod: one IN + two OUT int → void + two single-element arrays")
        void spDivmod_inPlusTwoOuts() throws SQLException {
            Byte[] quotient = new Byte[1];
            Byte[] remainder = new Byte[1];
            dataManager.call().spDivmod(dataSource, (byte) 17, quotient, remainder);
            Assertions.assertEquals(5, byteOrInt(quotient[0]));
            Assertions.assertEquals(2, byteOrInt(remainder[0]));
        }

        @ParameterizedTest(name = "n={0} → q={1}, r={2}")
        @CsvSource({"0, 0, 0", "3, 1, 0", "9, 3, 0", "18, 6, 0", "20, 6, 2"})
        @DisplayName("sp_divmod: quotient and remainder for representative values")
        void spDivmod_parameterized(final int n, final int expectedQ, final int expectedR) throws SQLException {
            Byte[] q = new Byte[1];
            Byte[] r = new Byte[1];
            dataManager.call().spDivmod(dataSource, (byte) n, q, r);
            Assertions.assertEquals(expectedQ, byteOrInt(q[0]));
            Assertions.assertEquals(expectedR, byteOrInt(r[0]));
        }

        @Test
        @DisplayName("sp_divmod: negative dividend (integer division toward zero in PostgreSQL)")
        void spDivmod_negativeDividend() throws SQLException {
            Byte[] q = new Byte[1];
            Byte[] r = new Byte[1];
            dataManager.call().spDivmod(dataSource, (byte) -10, q, r);
            Assertions.assertEquals(-3, byteOrInt(q[0]));
            Assertions.assertEquals(-1, byteOrInt(r[0]));
        }

        @Test
        @DisplayName("sp_sum_product: two IN + two OUT (multiple IN, multiple OUT)")
        void spSumProduct_twoInTwoOut() throws SQLException {
            Byte[] sumOut = new Byte[1];
            Byte[] productOut = new Byte[1];
            dataManager.call().spSumProduct(dataSource, (byte) 6, (byte) 7, sumOut, productOut);
            Assertions.assertEquals(13, byteOrInt(sumOut[0]));
            Assertions.assertEquals(42, byteOrInt(productOut[0]));
        }

        @Test
        @DisplayName("sp_sum_product: zero and identity")
        void spSumProduct_zeroAndIdentity() throws SQLException {
            Byte[] s1 = new Byte[1];
            Byte[] p1 = new Byte[1];
            dataManager.call().spSumProduct(dataSource, (byte) 0, (byte) 5, s1, p1);
            Assertions.assertEquals(5, byteOrInt(s1[0]));
            Assertions.assertEquals(0, byteOrInt(p1[0]));

            Byte[] s2 = new Byte[1];
            Byte[] p2 = new Byte[1];
            dataManager.call().spSumProduct(dataSource, (byte) 1, (byte) 1, s2, p2);
            Assertions.assertEquals(2, byteOrInt(s2[0]));
            Assertions.assertEquals(1, byteOrInt(p2[0]));
        }

        @Test
        @DisplayName("sp_sum_product: negative factors")
        void spSumProduct_negatives() throws SQLException {
            Byte[] s = new Byte[1];
            Byte[] p = new Byte[1];
            dataManager.call().spSumProduct(dataSource, (byte) -4, (byte) 3, s, p);
            Assertions.assertEquals(-1, byteOrInt(s[0]));
            Assertions.assertEquals(-12, byteOrInt(p[0]));
        }

        @Test
        @DisplayName("sp_fixed_pair: OUT-only procedure (no IN parameters)")
        void spFixedPair_outOnly() throws SQLException {
            Byte[] first = new Byte[1];
            Byte[] second = new Byte[1];
            dataManager.call().spFixedPair(dataSource, first, second);
            Assertions.assertEquals(21, byteOrInt(first[0]));
            Assertions.assertEquals(22, byteOrInt(second[0]));
        }

        @Test
        @DisplayName("sp_fixed_pair: repeated calls return the same constants")
        void spFixedPair_idempotent() throws SQLException {
            Byte[] a1 = new Byte[1];
            Byte[] b1 = new Byte[1];
            dataManager.call().spFixedPair(dataSource, a1, b1);
            Byte[] a2 = new Byte[1];
            Byte[] b2 = new Byte[1];
            dataManager.call().spFixedPair(dataSource, a2, b2);
            Assertions.assertEquals(byteOrInt(a1[0]), byteOrInt(a2[0]));
            Assertions.assertEquals(byteOrInt(b1[0]), byteOrInt(b2[0]));
        }
    }

    /**
     * <b>INOUT</b>: same JDBC ordinal is both set and registered as OUT; when it is the only output,
     * the generated method returns the updated value.
     */
    @Nested
    @DisplayName("INOUT")
    class InOutMode {

        @Test
        @DisplayName("sp_double_inout: single INOUT int → doubled value returned")
        void spDoubleInout_singleInout() throws SQLException {
            Byte doubled = dataManager.call().spDoubleInout(dataSource, (byte) 11);
            Assertions.assertEquals(22, byteOrInt(doubled));
        }

        @Test
        @DisplayName("sp_double_inout: zero stays zero")
        void spDoubleInout_zero() throws SQLException {
            Byte doubled = dataManager.call().spDoubleInout(dataSource, (byte) 0);
            Assertions.assertEquals(0, byteOrInt(doubled));
        }

        @Test
        @DisplayName("sp_double_inout: negative value")
        void spDoubleInout_negative() throws SQLException {
            Byte doubled = dataManager.call().spDoubleInout(dataSource, (byte) -7);
            Assertions.assertEquals(-14, byteOrInt(doubled));
        }

        @Test
        @DisplayName("sp_double_inout: small positive boundary")
        void spDoubleInout_smallPositive() throws SQLException {
            Byte doubled = dataManager.call().spDoubleInout(dataSource, (byte) 1);
            Assertions.assertEquals(2, byteOrInt(doubled));
        }
    }

    /**
     * <b>Procedure with IN arguments and side effects</b> — still IN-only from the JDBC caller’s
     * perspective (no OUT metadata). Current codegen maps {@code DECIMAL} arguments here to
     * {@link Byte}; amounts must fit in a byte unless types are regenerated.
     */
    @Nested
    @DisplayName("Transactional IN procedure (accounts)")
    class TransferProcedure {

        @BeforeEach
        void reseedAccounts() throws SQLException {
            resetSeedAccounts();
        }

        @Test
        @DisplayName("transfer: three IN (sender, receiver, amount) — balances updated")
        void transfer_updatesBothBalances() throws SQLException {
            Accounts senderBefore = accountsStore.select(dataSource, 1L).orElseThrow();
            Accounts receiverBefore = accountsStore.select(dataSource, 2L).orElseThrow();
            byte amount = 100;

            dataManager.call().transfer(dataSource, (byte) 1, (byte) 2, amount);

            Accounts senderAfter = accountsStore.select(dataSource, 1L).orElseThrow();
            Accounts receiverAfter = accountsStore.select(dataSource, 2L).orElseThrow();

            Assertions.assertEquals(senderBefore.balance() - amount, senderAfter.balance(), BALANCE_DELTA);
            Assertions.assertEquals(receiverBefore.balance() + amount, receiverAfter.balance(), BALANCE_DELTA);
        }

        @Test
        @DisplayName("transfer: re-seed then opposite direction")
        void transfer_isIdempotentWhenReSeeded() throws SQLException {
            dataManager.call().transfer(dataSource, (byte) 1, (byte) 2, (byte) 50);
            Assertions.assertEquals(SEED_BALANCE - 50,
                    accountsStore.select(dataSource, 1L).orElseThrow().balance(), BALANCE_DELTA);

            resetSeedAccounts();

            dataManager.call().transfer(dataSource, (byte) 2, (byte) 1, (byte) 25);
            Assertions.assertEquals(SEED_BALANCE - 25,
                    accountsStore.select(dataSource, 2L).orElseThrow().balance(), BALANCE_DELTA);
            Assertions.assertEquals(SEED_BALANCE + 25,
                    accountsStore.select(dataSource, 1L).orElseThrow().balance(), BALANCE_DELTA);
        }

        @Test
        @DisplayName("transfer: zero amount leaves balances unchanged")
        void transfer_zeroAmount_noOp() throws SQLException {
            Accounts before1 = accountsStore.select(dataSource, 1L).orElseThrow();
            Accounts before2 = accountsStore.select(dataSource, 2L).orElseThrow();
            dataManager.call().transfer(dataSource, (byte) 1, (byte) 2, (byte) 0);
            Assertions.assertEquals(before1.balance(),
                    accountsStore.select(dataSource, 1L).orElseThrow().balance(), BALANCE_DELTA);
            Assertions.assertEquals(before2.balance(),
                    accountsStore.select(dataSource, 2L).orElseThrow().balance(), BALANCE_DELTA);
        }

        @Test
        @DisplayName("transfer: two sequential transfers accumulate on receiver")
        void transfer_sequentialTransfers() throws SQLException {
            dataManager.call().transfer(dataSource, (byte) 1, (byte) 2, (byte) 10);
            dataManager.call().transfer(dataSource, (byte) 1, (byte) 2, (byte) 20);
            Assertions.assertEquals(SEED_BALANCE - 30,
                    accountsStore.select(dataSource, 1L).orElseThrow().balance(), BALANCE_DELTA);
            Assertions.assertEquals(SEED_BALANCE + 30,
                    accountsStore.select(dataSource, 2L).orElseThrow().balance(), BALANCE_DELTA);
        }

        @Test
        @DisplayName("transfer: round-trip sender → receiver → sender restores net (same magnitude)")
        void transfer_roundTrip() throws SQLException {
            dataManager.call().transfer(dataSource, (byte) 1, (byte) 2, (byte) 40);
            dataManager.call().transfer(dataSource, (byte) 2, (byte) 1, (byte) 40);
            Assertions.assertEquals(SEED_BALANCE,
                    accountsStore.select(dataSource, 1L).orElseThrow().balance(), BALANCE_DELTA);
            Assertions.assertEquals(SEED_BALANCE,
                    accountsStore.select(dataSource, 2L).orElseThrow().balance(), BALANCE_DELTA);
        }
    }
}

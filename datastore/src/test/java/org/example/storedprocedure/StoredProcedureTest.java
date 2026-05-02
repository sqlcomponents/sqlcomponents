package org.example.storedprocedure;

import org.example.DataManager;
import org.example.model.Accounts;
import org.example.model.Cache;
import org.example.store.AccountsStore;
import org.example.store.CacheStore;
import org.example.util.DataSourceProvider;
import org.example.util.EncryptionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
 * <b>Reflection</b>: Tests for routines added after your last codegen use reflection so this module still
 * compiles before you re-run the generator. After regeneration, those tests execute normally; if a method
 * is still missing, the test is skipped via {@link Assumptions}.
 */
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

    /**
     * Locates a {@link DataManager.Procedure} instance method by name and total parameter count
     * ({@code DataSource} plus application parameters).
     */
    private static Method procedureMethod(final String name, final int totalParams) {
        return Arrays.stream(DataManager.Procedure.class.getMethods())
                .filter(m -> Objects.equals(DataManager.Procedure.class, m.getDeclaringClass()))
                .filter(m -> name.equals(m.getName()))
                .filter(m -> m.getParameterCount() == totalParams)
                .findFirst()
                .orElse(null);
    }

    private static Object[] prependDataSource(final DataSource ds, final Object[] tail) {
        final Object[] args = new Object[tail.length + 1];
        args[0] = ds;
        System.arraycopy(tail, 0, args, 1, tail.length);
        return args;
    }

    private static int toInt(final Object holderSlot0) {
        return ((Number) holderSlot0).intValue();
    }

    private static Object singleOutArray(final Class<?> componentType) {
        return Array.newInstance(componentType, 1);
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

    /**
     * <b>IN only</b>: parameters are inputs; no registered OUT columns — {@code void} on {@code Procedure}.
     */
    @Nested
    @DisplayName("IN-only procedures (void call)")
    class InOnly {

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
        @DisplayName("fn_sum_three: three IN parameters, scalar return (IN + IN + IN)")
        void fnSum_threeInts() throws Exception {
            final Method m = procedureMethod("fnSumThree", 4);
            Assumptions.assumeTrue(m != null, "Regenerate DataManager after applying procedures.sql (fn_sum_three)");
            final Object out = m.invoke(dataManager.call(), prependDataSource(dataSource,
                    new Object[]{(byte) 2, (byte) 3, (byte) 4}));
            Assertions.assertEquals(9, ((Number) out).intValue());
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
        void spEchoLen_inPlusSingleOut() throws Exception {
            final Method m = procedureMethod("spEchoLen", 2);
            Assumptions.assumeTrue(m != null, "Regenerate DataManager after applying procedures.sql (sp_echo_len)");
            final Object len = m.invoke(dataManager.call(), dataSource, "abcde");
            Assertions.assertEquals(5, ((Number) len).intValue());
        }

        @Test
        @DisplayName("sp_divmod: one IN + two OUT int → void + two single-element arrays")
        void spDivmod_inPlusTwoOuts() throws Exception {
            final Method m = procedureMethod("spDivmod", 4);
            Assumptions.assumeTrue(m != null, "Regenerate DataManager after applying procedures.sql (sp_divmod)");
            final Class<?>[] pt = m.getParameterTypes();
            final Object qHolder = singleOutArray(pt[2].getComponentType());
            final Object rHolder = singleOutArray(pt[3].getComponentType());
            m.invoke(dataManager.call(), prependDataSource(dataSource, new Object[]{(byte) 17, qHolder, rHolder}));
            Assertions.assertEquals(5, toInt(Array.get(qHolder, 0)));
            Assertions.assertEquals(2, toInt(Array.get(rHolder, 0)));
        }

        @Test
        @DisplayName("sp_sum_product: two IN + two OUT (multiple IN, multiple OUT)")
        void spSumProduct_twoInTwoOut() throws Exception {
            final Method m = procedureMethod("spSumProduct", 5);
            Assumptions.assumeTrue(m != null, "Regenerate DataManager after applying procedures.sql (sp_sum_product)");
            final Class<?>[] pt = m.getParameterTypes();
            final Object sumHolder = singleOutArray(pt[3].getComponentType());
            final Object prodHolder = singleOutArray(pt[4].getComponentType());
            m.invoke(dataManager.call(), prependDataSource(dataSource,
                    new Object[]{(byte) 6, (byte) 7, sumHolder, prodHolder}));
            Assertions.assertEquals(13, ((Number) Array.get(sumHolder, 0)).intValue());
            Assertions.assertEquals(42, ((Number) Array.get(prodHolder, 0)).intValue());
        }

        @Test
        @DisplayName("sp_fixed_pair: OUT-only procedure (no IN parameters)")
        void spFixedPair_outOnly() throws Exception {
            final Method m = procedureMethod("spFixedPair", 3);
            Assumptions.assumeTrue(m != null, "Regenerate DataManager after applying procedures.sql (sp_fixed_pair)");
            final Class<?>[] pt = m.getParameterTypes();
            final Object aHolder = singleOutArray(pt[1].getComponentType());
            final Object bHolder = singleOutArray(pt[2].getComponentType());
            m.invoke(dataManager.call(), prependDataSource(dataSource, new Object[]{aHolder, bHolder}));
            Assertions.assertEquals(21, ((Number) Array.get(aHolder, 0)).intValue());
            Assertions.assertEquals(22, ((Number) Array.get(bHolder, 0)).intValue());
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
        void spDoubleInout_singleInout() throws Exception {
            final Method m = procedureMethod("spDoubleInout", 2);
            Assumptions.assumeTrue(m != null,
                    "Regenerate DataManager after applying procedures.sql (sp_double_inout)");
            final Object doubled = m.invoke(dataManager.call(), dataSource, (byte) 11);
            Assertions.assertEquals(22, ((Number) doubled).intValue());
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
    }
}

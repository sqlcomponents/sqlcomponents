package org.sqlcomponents.compiler.java.mapper.postgresql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.postgresql.ds.PGPoolingDataSource;
import org.sqlcomponents.compiler.java.JavaCompiler;
import org.sqlcomponents.compiler.java.util.CompilerTestUtil;
import org.sqlcomponents.core.model.Application;

import javax.sql.DataSource;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class DataTypeTest<T> {

    private final Class<?> myTableStoreClass;
    private final Class<?> myTableClass;

    private final Object myTableStore;

    DataTypeTest() {
        try {
            final Application application = CompilerTestUtil.getApplication();
            DataSource dataSource = dataSource(application);

            dropTable(dataSource);
            createTable(dataSource);

            application.setTablePatterns(List.of("my_table"));
            application.compile(new JavaCompiler());

            javax.tools.JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            DiagnosticCollector<JavaFileObject> ds = new DiagnosticCollector<>();

            try (StandardJavaFileManager mgr = compiler.getStandardFileManager(ds, null, null)) {
                Iterable<? extends JavaFileObject> sources = mgr.getJavaFileObjectsFromFiles(Arrays.asList(new File(application.getSrcFolder(), "org/example/DataManager.java"), new File(application.getSrcFolder(), "org/example/store/MyTableStore.java"), new File(application.getSrcFolder(), "org/example/model/MyTable.java")));
                javax.tools.JavaCompiler.CompilationTask task = compiler.getTask(null, mgr, ds, null, null, sources);
                task.call();

                URLClassLoader classLoader = new URLClassLoader(new URL[]{new File(application.getSrcFolder()).toURI().toURL()});
                // Load the class from the classloader by name....
                Class<?> loadedClass = classLoader.loadClass("org.example.DataManager");

                myTableClass = classLoader.loadClass("org.example.model.MyTable");

                Method method = loadedClass.getMethod("getManager", DataSource.class, Function.class, Function.class);
                Object dataManager = method.invoke(null, dataSource, null, null);

                this.myTableStoreClass = classLoader.loadClass("org.example.store.MyTableStore");

                this.myTableStore = loadedClass.getMethod("getMyTableStore").invoke(dataManager);

            } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException |
                     IllegalAccessException | IOException e) {
                throw new RuntimeException(e);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    abstract Set<T> values();


    abstract String dataType();

    @BeforeEach
    void deleteAll() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method deleteMethod = myTableStoreClass.getMethod("delete");
        Class<?> deleteStatementClass = deleteMethod.getReturnType();
        Object deleteStmtObj = deleteMethod.invoke(myTableStore);
        deleteStatementClass.getMethod("execute").invoke(deleteStmtObj);
    }

    @Test
    void testNull() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        insert(null);
        Assertions.assertNull(getValue(getMyTable()));
    }

    @ParameterizedTest
    @MethodSource("values")
    void testValue(T value) throws Exception {
        insert(value);
        Assertions.assertEquals(value, getValue(getMyTable()));
    }

    private Object getMyTable() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method selecttMethod = myTableStoreClass.getMethod("select");
        Object selectStmtObj = selecttMethod.invoke(myTableStore);

        return ((List<Object>) selecttMethod.getReturnType().getMethod("execute").invoke(selectStmtObj)).get(0);
    }

    private void insert(final T value) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Object myTableObj = getMyTableObject(value);

        Method insertMethod = myTableStoreClass.getMethod("insert");
        Object insertStmtObj = insertMethod.invoke(myTableStore);

        Method valuesMethod = insertMethod.getReturnType().getMethod("values", myTableClass);
        Object valuesStmtObj = valuesMethod.invoke(insertStmtObj, myTableObj);

        valuesMethod.getReturnType().getMethod("execute").invoke(valuesStmtObj);
    }

    private Object getMyTableObject(final T value) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return myTableClass.getConstructor(values().iterator().next().getClass()).newInstance(value);
    }

    private Object getValue(final Object myTable) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return myTable.getClass().getMethod("getMyValue").invoke(myTable);
    }

    private void createTable(final DataSource dataSource) throws SQLException {
        final String tableCreateSQL = "CREATE TABLE my_table(my_value " + dataType() + ")";
        final Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(tableCreateSQL);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }

    private void dropTable(final DataSource dataSource) throws SQLException {
        final String tableCreateSQL = "DROP TABLE IF EXISTS my_table";
        final Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(tableCreateSQL);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }

    private DataSource dataSource(Application application) {

        PGPoolingDataSource source = new PGPoolingDataSource();
        source.setDataSourceName("A Data Source");
        source.setServerNames(new String[] {
                "localhost"
        });
        source.setDatabaseName("moviedb");
        source.setUser("moviedb");
        source.setPassword("moviedb");
        source.setMaxConnections(10);

        return source;
    }

}

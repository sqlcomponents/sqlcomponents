

package org.example;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.example.store.AccountsStore;
import org.example.store.CacheStore;
import org.example.store.MaterializedMovieViewStore;
import org.example.store.MovieStore;
import org.example.store.MovieViewStore;
public final class DataManager {
    /**
    * dataManager variable.
    */
    private static DataManager dataManager;

    /**
    * observer variable.
    */
    private final Observer observer;
    /**
    * procedure variable.
    */
    private final Procedure procedure;

    /**
    * materializedMovieViewStore variable.
    */
    private final MaterializedMovieViewStore materializedMovieViewStore;
    /**
    * accountsStore variable.
    */
    private final AccountsStore accountsStore;
    /**
    * cacheStore variable.
    */
    private final CacheStore cacheStore;
    /**
    * movieStore variable.
    */
    private final MovieStore movieStore;
    /**
    * movieViewStore variable.
    */
    private final MovieViewStore movieViewStore;

    private DataManager(
     final Function<String, String> encryptionFunction,
     final Function<String, String> decryptionFunction
    ) {
        this.observer = new Observer();
        this.procedure = new Procedure();
        this.materializedMovieViewStore = MaterializedMovieViewStore
.getMaterializedMovieViewStore(this, this.observer);
        this.accountsStore = AccountsStore
.getAccountsStore(this, this.observer);
        this.cacheStore = CacheStore
.getCacheStore(this, this.observer);
        this.movieStore = MovieStore
.getMovieStore(this, this.observer);
        this.movieViewStore = MovieViewStore
.getMovieViewStore(this, this.observer);
    }
     /**
     * getManager method.
     * @param encryptionFunction
     * @param decryptionFunction
     * @return dataManager
     */
    public static DataManager getManager(
     final Function<String, String> encryptionFunction,
     final Function<String, String> decryptionFunction
                                                            ) {
        if (dataManager == null) {
            dataManager = new DataManager(
             encryptionFunction,
             decryptionFunction
            );
        }
        return dataManager;
    }


    /**
     * MaterializedMovieViewStore.
     * @return materializedMovieViewStore
     */
    public MaterializedMovieViewStore getMaterializedMovieViewStore() {
        return this.materializedMovieViewStore;
    }
    /**
     * AccountsStore.
     * @return accountsStore
     */
    public AccountsStore getAccountsStore() {
        return this.accountsStore;
    }
    /**
     * CacheStore.
     * @return cacheStore
     */
    public CacheStore getCacheStore() {
        return this.cacheStore;
    }
    /**
     * MovieStore.
     * @return movieStore
     */
    public MovieStore getMovieStore() {
        return this.movieStore;
    }
    /**
     * MovieViewStore.
     * @return movieViewStore
     */
    public MovieViewStore getMovieViewStore() {
        return this.movieViewStore;
    }

    public interface Column<T> {
      /**
       * String name.
       * @return name
       */
        String name();
       /**
        * String asSql.
        * @return name
        */
        String asSql();
         /**
         * String validate.
         * @param value
         * @return name
         */
        boolean validate(T value);
       /**
        *  set method.
        * @param preparedStatement
        * @param value
        * @throws SQLException
        */
        void set(SqlBuilder preparedStatement,
        T value) ;
        /**
         *  T get method.
         * @param resultSet
         * @param i
         * @return get
         * @throws SQLException
         */
        T get(ResultSet resultSet, int i) throws SQLException;

    }
     /**
     *  Value Class.
     * @param <T>
     * @param <R>
     */
    public static class Value<T extends Column<R>, R> {

    /**
    * column variable.
    */
        private final T column;
    /**
    * value variable.
    */
        private final R value;
        /**
        * Value method.
        * @param columnT
        * @param valueR
        */
        public Value(final T columnT, final R valueR) {
            this.column = columnT;
            this.value = valueR;
        }
         /**
         * T column method.
         * @return column
         */
        public T column() {
            return column;
        }
          /**
          * set method.
          * @param preparedStatement
          * @throws SQLException
          */
        public void set(final DataManager.SqlBuilder preparedStatement) {
            column.set(preparedStatement, value);
        }
    }

    public final class Observer {
        // Observer is internal
        // This also prevents store creation outside DataManager
        private Observer() {

        }
    }

    @FunctionalInterface
    public interface ConvertFunction<T, Object> {

        /**
         * apply method.
         * @param t
         * @return apply
         * @throws SQLException
         */
        Object apply(T t) throws SQLException;
    }
         /**
          * GetFunction method.
          * @param <ResultSet>
          * @param <Integer>
          * @param <R>
          */
    @FunctionalInterface
    public interface GetFunction<ResultSet, Integer, R> {
           /**
            * R apply interface.
            * @param t
            * @param u
            * @return apply
            * @throws SQLException
            */

        R apply(ResultSet t, Integer u) throws SQLException;
    }

    /**
     * Page method.
     * @param content
     * @param totalElements
     * @return Page
     * @param <T>
     */
    public static <T> Page<T> page(final List<T> content,
    final int totalElements) {
        return new Page(content, totalElements);
    }

    public static final class Page<T> {
    /**
    *   content variable.
    */
        private final List<T> content;
     /**
     *   totalElements variable.
     */
        private final int totalElements;

        private Page(final List<T> contentT, final int totalElementsI) {
            this.content = contentT;
            this.totalElements = totalElementsI;
        }
            /**
            * getContent method.
            * @return content
            */
        public List<T> getContent() {
            return content;
        }
        /**
         * getTotalElements method.
         * @return totalElements
         */
        public int getTotalElements() {
            return totalElements;
        }
    }


/**
* Calls a stored procedure.
* @return procedure
*/
public Procedure call() {
    return this.procedure;
}

public static final class Procedure {


    private Procedure() {
    }

    /**
    * createCache Method.
    * @param code
    * @param cache
    */
    public void createCache(
        final DataSource dbDataSource,
     final String code, 
     final String cache 
    
    ) throws SQLException {
        try (CallableStatement callableStatement = dbDataSource.getConnection()
        .prepareCall("call create_cache(?,?)")) {
                      callableStatement.setString(1, code);
                      callableStatement.setString(2, cache);
            callableStatement.execute();
        } catch (SQLException e) {
            throw e;
        }
    }
    /**
    * transfer Method.
    * @param sender
    * @param receiver
    * @param amount
    */
    public void transfer(
        final DataSource dbDataSource,
     final Byte sender, 
     final Byte receiver, 
     final Byte amount 
    
    ) throws SQLException {
        try (CallableStatement callableStatement = dbDataSource.getConnection()
        .prepareCall("call transfer(?,?,?)")) {
                      callableStatement.setByte(1, sender);
                      callableStatement.setByte(2, receiver);
                      callableStatement.setByte(3, amount);
            callableStatement.execute();
        } catch (SQLException e) {
            throw e;
        }
    }
    /**
    * add Method.
    * @param a
    * @param b
    */
    public void add(
        final DataSource dbDataSource,
     final Byte a, 
     final Byte b 
    ,
    Byte returnvalue
    ) throws SQLException {
        try (CallableStatement callableStatement = dbDataSource.getConnection()
        .prepareCall("call add(?,?)")) {
                      callableStatement.setByte(1, a);
                      callableStatement.setByte(2, b);
                      callableStatement.registerOutParameter(3, java.sql.Types.INTEGER );
            callableStatement.execute();
                  returnvalue = callableStatement.getByte(3 );
        } catch (SQLException e) {
            throw e;
        }
    }
}
    /**
    * Static factory method to create a new SqlBuilder instance.
    *
    * @param sql the SQL query to be prepared
    * @return an instance of SqlBuilder
    */
    public SqlBuilder sql(final String sql) {
        return new SqlBuilder(sql);
    }

/**
 * The Sql interface represents a generic SQL operation that can be executed
 * using a JDBC Connection.
 *
 * @param <R> the type of the result returned by the SQL operation
 */
public interface Sql<R> {
    /**
     * Executes the SQL operation using the provided JDBC connection.
     *
     * @param connection the JDBC connection to use for executing the operation
     * @return the result of the SQL operation
     * @throws SQLException if an SQL error occurs during the execution
     */
    R execute(Connection connection) throws SQLException;

    /**
     * Executes the SQL operation using the provided JDBC dataSource.
     *
     * @param dataSource the JDBC dataSource to use for executing the operation
     * @return the result of the SQL operation
     * @throws SQLException if an SQL error occurs during the execution
     */
    default R execute(final DataSource dataSource) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            return execute(conn);
        }
    }
}

/**
 * SqlBuilder is a utility class that simplifies the process of constructing
 * SQL queries with dynamic parameters and executing them. It helps developers
 * manage SQL queries more efficiently with less boilerplate code, supporting
 * both query execution and parameterized updates.
 */
public class SqlBuilder implements Sql<Integer> {

    /**
     * The SQL query to be executed.
     */
    private final String sql;
    /**
     * A list of parameters for the query.
     */
    private final List<ParamMapper<?>> paramMappers;

    /**
     * Constructor that initializes the SqlBuilder with a given SQL query.
     *
     * @param theSql the SQL query to be prepared and executed
     */
    public SqlBuilder(final String theSql) {
        this.sql = theSql;
        this.paramMappers = new ArrayList<>();
    }

     /**
     * Adds a parameter with a null
     *
     * @return the current SqlBuilder instance, for method chaining
     */
    public SqlBuilder paramNull() {
        final int index = this.paramMappers.size() + 1;
        this.paramMappers
                .add((preparedStatement)
                        -> preparedStatement.setObject(index, null));
        return this;
    }

    /**
    * Adds a parameter with a specific SQL type and type name as `NULL` to the SQL query.
    * This method is used when the SQL parameter should be set to `NULL` for types
    * that require a type name in addition to the SQL type, such as SQL `STRUCT` or `ARRAY`.
    *
    * @param sqlType  the SQL type of the parameter, as defined in {@link java.sql.Types}
    * @param typeName the type name of the parameter, used for SQL types that require specific type information
    * @return the current SqlBuilder instance, for method chaining
    */
    public SqlBuilder paramNull(final int sqlType, final String typeName) {
        final int index = this.paramMappers.size() + 1;
        this.paramMappers
                .add((preparedStatement)
                        -> preparedStatement.setNull(index, sqlType, typeName));
        return this;
    }


    /**
 * Adds a Float parameter to the SQL query.
 *
 * @param value the Float value to be added
 * @return the current SqlBuilder instance, for method chaining
 */
public SqlBuilder param(final Float value) {
    final int index = this.paramMappers.size() + 1;
    this.paramMappers.add(preparedStatement -> preparedStatement.setFloat(index, value));
    return this;
}

/**
 * Adds a byte array parameter to the SQL query.
 *
 * @param value the byte array to be added
 * @return the current SqlBuilder instance, for method chaining
 */
public SqlBuilder param(final byte[] value) {
    final int index = this.paramMappers.size() + 1;
    this.paramMappers.add(preparedStatement -> preparedStatement.setBytes(index, value));
    return this;
}

/**
 * Adds a BigDecimal parameter to the SQL query.
 *
 * @param value the BigDecimal value to be added
 * @return the current SqlBuilder instance, for method chaining
 */
public SqlBuilder param(final java.math.BigDecimal value) {
    final int index = this.paramMappers.size() + 1;
    this.paramMappers.add(preparedStatement -> preparedStatement.setBigDecimal(index, value));
    return this;
}

/**
 * Adds a Time parameter to the SQL query.
 *
 * @param value the Time value to be added
 * @return the current SqlBuilder instance, for method chaining
 */
public SqlBuilder param(final java.sql.Time value) {
    final int index = this.paramMappers.size() + 1;
    this.paramMappers.add(preparedStatement -> preparedStatement.setTime(index, value));
    return this;
}

/**
 * Adds a Timestamp parameter to the SQL query.
 *
 * @param value the Timestamp value to be added
 * @return the current SqlBuilder instance, for method chaining
 */
public SqlBuilder param(final java.sql.Timestamp value) {
    final int index = this.paramMappers.size() + 1;
    this.paramMappers.add(preparedStatement -> preparedStatement.setTimestamp(index, value));
    return this;
}

    /**
     * Adds an Object parameter to the SQL query with targetSqlType.
     *
     * @param value the Object value to be added
     * @param targetSqlType the targeted SqlType.          
     * @return the current SqlBuilder instance, for method chaining
     */
    public SqlBuilder param(final Object value, final int targetSqlType) {
        final int index = this.paramMappers.size() + 1;
        this.paramMappers.add(preparedStatement
                -> preparedStatement.setObject(index, value, targetSqlType));
        return this;
    }

/**
 * Adds an Object parameter to the SQL query.
 *
 * @param value the Object value to be added
 * @return the current SqlBuilder instance, for method chaining
 */
public SqlBuilder param(final Object value) {
    final int index = this.paramMappers.size() + 1;
    this.paramMappers.add(preparedStatement -> preparedStatement.setObject(index, value));
    return this;
}


    /**
     * Adds a parameter to the SQL query. The method allows chaining and is used
     * to bind values to placeholders in the SQL query.
     *
     * @param value the value of the parameter to be added
     * @return the current SqlBuilder instance, for method chaining
     */
    public SqlBuilder param(final Integer value) {
        final int index = this.paramMappers.size() + 1;
        this.paramMappers
                .add((preparedStatement)
                        -> preparedStatement.setInt(index, value));
        return this;
    }

    /**
     * Adds a Short parameter to the SQL query.
     *
     * @param value the Short value to be added
     * @return the current SqlBuilder instance, for method chaining
     */
    public SqlBuilder param(final Short value) {
        final int index = this.paramMappers.size() + 1;
        this.paramMappers.add(preparedStatement -> preparedStatement.setShort(index, value));
        return this;
    }

    /**
     * Adds a parameter to the SQL query. The method allows chaining and is used
     * to bind values to placeholders in the SQL query.
     *
     * @param value the value of the parameter to be added
     * @return the current SqlBuilder instance, for method chaining
     */
    public SqlBuilder param(final String value) {
        final int index = this.paramMappers.size() + 1;
        this.paramMappers
                .add((preparedStatement)
                        -> preparedStatement.setString(index, value));
        return this;
    }
    /**
     * Adds a Double parameter to the SQL query.
     *
     * @param value the Double value to be added
     * @return the current SqlBuilder instance, for method chaining
     */
    public SqlBuilder param(final Double value) {
        final int index = this.paramMappers.size() + 1;
        this.paramMappers.add(preparedStatement -> preparedStatement.setDouble(index, value));
        return this;
    }

    /**
     * Adds a Boolean parameter to the SQL query.
     *
     * @param value the Boolean value to be added
     * @return the current SqlBuilder instance, for method chaining
     */
    public SqlBuilder param(final Boolean value) {
        final int index = this.paramMappers.size() + 1;
        this.paramMappers.add(preparedStatement -> preparedStatement.setBoolean(index, value));
        return this;
    }

    /**
     * Adds a Long parameter to the SQL query.
     *
     * @param value the Long value to be added
     * @return the current SqlBuilder instance, for method chaining
     */
    public SqlBuilder param(final Long value) {
        final int index = this.paramMappers.size() + 1;
        this.paramMappers.add(preparedStatement -> preparedStatement.setLong(index, value));
        return this;
    }

    /**
     * Adds a Date parameter to the SQL query.
     *
     * @param value the Date value to be added
     * @return the current SqlBuilder instance, for method chaining
     */
    public SqlBuilder param(final java.sql.Date value) {
        final int index = this.paramMappers.size() + 1;
        this.paramMappers.add(preparedStatement -> preparedStatement.setDate(index, value));
        return this;
    }
    /**
     * Executes an update (such as INSERT, UPDATE, DELETE) using the prepared
     * SQL query
     * and the bound parameters.
     *
     * @param connection the database connection used to execute the query
     * @return the number of rows affected by the update
     * @throws SQLException if a database access error occurs
     */
    @Override
    public Integer execute(final Connection connection) throws SQLException {
        int updatedRows;
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(sql)) {
            prepare(preparedStatement);
            updatedRows = preparedStatement.executeUpdate();
        }
        return updatedRows;
    }

/**
* Provides if record exists.
*
* @return a new Query instance for execution
*/
public SingleValueQuery<Boolean> queryForExists() {
    return new SingleValueQuery<>() {
        @Override
        public Boolean execute(final Connection connection) throws SQLException {
            boolean exists;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                prepare(preparedStatement);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    exists = resultSet.next();
                }
            }
            return exists;
        }

        @Override
        Boolean getValue(final ResultSet resultSet) throws SQLException {
            return resultSet.next();
        }
    };
}

    /**
     * Creates a new Query object that can be used to execute
     * a SELECT query and map the result set to an Integer.
     *
     * @return a new Query instance for execution
     */
    public SingleValueQuery<Integer> queryForInt() {
        return new SingleValueQuery<>() {
            @Override
            Integer getValue(final ResultSet resultSet) throws SQLException {
                return resultSet.getInt(1);
            }
        };
    }

    /**
     * Creates a new Query object that can be used to execute
     * a SELECT query and map the result set to a String.
     *
     * @return a new Query instance for execution
     */
    public SingleValueQuery<String> queryForString() {
        return new SingleValueQuery<>() {
            @Override
            String getValue(final ResultSet resultSet) throws SQLException {
                return resultSet.getString(1);
            }
        };
    }

    /**
     * Creates a new Query object that can be used to execute
     * a SELECT query and map the result set to a Double.
     *
     * @return a new Query instance for execution
     */
    public SingleValueQuery<Double> queryForDouble() {
        return new SingleValueQuery<>() {
            @Override
            Double getValue(final ResultSet resultSet) throws SQLException {
                return resultSet.getDouble(1);
            }
        };
    }

    /**
     * Creates a new Query object that can be used to execute
     * a SELECT query and map the result set to a Boolean.
     *
     * @return a new Query instance for execution
     */
    public SingleValueQuery<Boolean> queryForBoolean() {
        return new SingleValueQuery<>() {
            @Override
            Boolean getValue(final ResultSet resultSet) throws SQLException {
                return resultSet.getBoolean(1);
            }
        };
    }

    /**
     * Creates a new Query object that can be used to execute
     * a SELECT query and map the result set to a Long.
     *
     * @return a new Query instance for execution
     */
    public SingleValueQuery<Long> queryForLong() {
        return new SingleValueQuery<>() {
            @Override
            Long getValue(final ResultSet resultSet) throws SQLException {
                return resultSet.getLong(1);
            }
        };
    }

    /**
     * Creates a new Query object that can be used to execute
     * a SELECT query and map the result set to a Date.
     *
     * @return a new Query instance for execution
     */
    public SingleValueQuery<java.sql.Date> queryForDate() {
        return new SingleValueQuery<>() {
            @Override
            java.sql.Date getValue(final ResultSet resultSet) throws SQLException {
                return resultSet.getDate(1);
            }
        };
    }

    /**
     * Creates a new Query object that can be used to execute
     * a SELECT query and map the result set to an Object.
     *
     * @return a new Query instance for execution
     */
    public SingleValueQuery<Object> queryForObject() {
        return new SingleValueQuery<>() {
            @Override
            Object getValue(final ResultSet resultSet) throws SQLException {
                return resultSet.getObject(1);
            }
        };
    }

    /**
     * Creates a new Query object that can be used to execute
     * a SELECT query and map the result set to a specific object type
     * using the provided RowMapper.
     *
     * @param <T> the type of object to map the result set to
     * @param rowMapper an implementation of
     *                  RowMapper to map each row of the result set
     * @return a new Query instance for execution
     */
    public <T> SqlBuilder.SingleRecordQuery<T> queryForOne(
            final RowMapper<T> rowMapper) {
        return this.new SingleRecordQuery<>(rowMapper);
    }

    /**
     * Creates a new Query object that can be used to execute
     * a SELECT query and map the result set to list of a specific object type
     * using the provided RowMapper.
     *
     * @param <T> the type of object to map the result set to
     * @param rowMapper an implementation of
     *                  RowMapper to map each row of the result set
     * @return a new Query instance for execution
     */
    public <T> SqlBuilder.MultipleRecordQuery<T> queryForList(
            final RowMapper<T> rowMapper) {
        return this.new MultipleRecordQuery<>(rowMapper);
    }

    /**
     * RowMapper is an interface that defines how to map each row of a ResultSet
     * to a Java object.
     *
     * @param <T> the type of object to map the result set to
     */
    public interface RowMapper<T> {
        /**
         * Maps a single row of the result set to an object.
         *
         * @param resultSet the result set obtained from executing the SQL query
         * @return the mapped object
         * @throws SQLException if an SQL error occurs during mapping
         */
        T mapRow(ResultSet resultSet) throws SQLException;
    }

    /**
     * Functional interface representing a parameter mapper
     * that binds parameters
     * to a {@link PreparedStatement}. Implementations of this interface are
     * responsible for mapping a specific parameter
     * to the appropriate placeholder
     * in the SQL query.
     *
     * @param <T> the type of the parameter to be mapped
     */
    public interface ParamMapper<T> {

        /**
         * Binds the provided parameters to the placeholders in the given
         * {@link PreparedStatement}. This method is called to map and set
         * parameter values for the SQL query.
         *
         * @param preparedStatement the {@link PreparedStatement}
         *                          to bind parameters to
         * @throws SQLException if a database access error occurs or if
         *                      parameter binding fails
         */
        void mapParam(PreparedStatement preparedStatement) throws SQLException;
    }

    /**
     * The Query class encapsulates the logic for executing
     * SELECT queries and mapping
     * the results to Java objects using a RowMapper.
     *
     * @param <T> the type of object to map the result set to
     */
    public static class Query<T> {

        /**
         * Mapper for Result set.
         */
        private final RowMapper<T> rowMapper;

        /**
         * Private constructor for creating a Query instance with
         * the specified RowMapper.
         *
         * @param theRowMapper the RowMapper used to map the ResultSet rows
         */
        private Query(final RowMapper<T> theRowMapper) {
            this.rowMapper = theRowMapper;
        }

        T mapRow(final ResultSet resultSet) throws SQLException {
            return rowMapper.mapRow(resultSet);
        }
    }

    /**
     * Query to get Single Value.
     * @param <T> type of value
     */
    public abstract class SingleValueQuery<T> extends SingleRecordQuery<T> {

        /**
         * Private constructor for creating a Query instance.
         */
        private SingleValueQuery() {
            super(null);
        }

        @Override
        T mapRow(final ResultSet resultSet) throws SQLException {
            return getValue(resultSet);
        }

        /**
         * Gets Value from Result set.
         * @param resultSet
         * @return value
         * @throws SQLException
         */
        abstract T getValue(ResultSet resultSet) throws SQLException;
    }

    public class SingleRecordQuery<T> extends Query<T> implements Sql<T> {

        /**
         * Private constructor for creating a Query instance with
         * the specified RowMapper.
         *
         * @param theRowMapper the RowMapper used to map the ResultSet rows
         */
        private SingleRecordQuery(final RowMapper<T> theRowMapper) {
            super(theRowMapper);
        }

        /**
         * Executes the SQL query and returns a single mapped result
         * from the ResultSet.
         *
         * @param connection the database connection used to execute the query
         * @return the single mapped result, or null if no result is found
         * @throws SQLException if a database access error occurs
         */
        @Override
        public T execute(final Connection connection) throws SQLException {
            T result = null;
            try (PreparedStatement preparedStatement
                         = connection.prepareStatement(sql)) {
                prepare(preparedStatement);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        result = mapRow(resultSet);
                    }
                }
            }
            return result;
        }
    }

    public final class MultipleRecordQuery<T>
            extends Query<T> implements Sql<List<T>> {

        /**
         * Private constructor for creating a Query instance with
         * the specified RowMapper.
         *
         * @param theRowMapper the RowMapper used to map the ResultSet rows
         */
        private MultipleRecordQuery(final RowMapper<T> theRowMapper) {
            super(theRowMapper);
        }

        /**
         * Executes the SQL query and returns a list of mapped result
         * from the ResultSet.
         *
         * @param connection the database connection used to execute the query
         * @return the list of mapped result, or empty if no result is found
         * @throws SQLException if a database access error occurs
         */
        @Override
        public List<T> execute(final Connection connection)
                throws SQLException {
            List<T> result = new ArrayList<>();
            try (PreparedStatement preparedStatement
                         = connection.prepareStatement(sql)) {
                prepare(preparedStatement);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                   while (resultSet.next()) {
                       result.add(mapRow(resultSet));
                   }
                }
            }
            return result;
        }
    }

   /**
     * Creates a new Generated Keys object that can be used to execute
     * a SELECT query and map the result set to a specific object type
     * using the provided RowMapper.
     *
     * @param <T> the type of object to map the result set to
     * @param rowMapper an implementation of
     *                  RowMapper to map each row of the result set
     * @return a new Query instance for execution
     */
    public <T> SqlBuilder.SingleGeneratedKeysQuery<T> queryGeneratedKeysForOne(
            final RowMapper<T> rowMapper) {
        return this.new SingleGeneratedKeysQuery<>(rowMapper);
    }

    /**
     * Creates a new Generated Keys object that can be used to execute
     * a SELECT query and map the result set to list of a specific object type
     * using the provided RowMapper.
     *
     * @param <T> the type of object to map the result set to
     * @param rowMapper an implementation of
     *                  RowMapper to map each row of the result set
     * @return a new Query instance for execution
     */
    public <T> SqlBuilder.MultipleGeneratedKeysQuery<T> queryGeneratedKeysForList(
            final RowMapper<T> rowMapper) {
        return this.new MultipleGeneratedKeysQuery<>(rowMapper);
    }

    public class SingleGeneratedKeysQuery<T> extends Query<T> implements Sql<T> {

        /**
         * Private constructor for creating a Query instance with
         * the specified RowMapper.
         *
         * @param theRowMapper the RowMapper used to map the ResultSet rows
         */
        private SingleGeneratedKeysQuery(final RowMapper<T> theRowMapper) {
            super(theRowMapper);
        }

        /**
         * Executes the SQL query and returns a single mapped result
         * from the ResultSet.
         *
         * @param connection the database connection used to execute the query
         * @return the single mapped result, or null if no result is found
         * @throws SQLException if a database access error occurs
         */
        @Override
        public T execute(final Connection connection) throws SQLException {
            T result = null;
            try (PreparedStatement preparedStatement
                         = connection.prepareStatement(sql
                    , java.sql.Statement.RETURN_GENERATED_KEYS)) {
                prepare(preparedStatement);
                preparedStatement.executeUpdate();
                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        result = mapRow(resultSet);
                    }
                }
            }
            return result;
        }
    }

    public final class MultipleGeneratedKeysQuery<T>
            extends Query<T> implements Sql<List<T>> {

        /**
         * Private constructor for creating a Query instance with
         * the specified RowMapper.
         *
         * @param theRowMapper the RowMapper used to map the ResultSet rows
         */
        private MultipleGeneratedKeysQuery(final RowMapper<T> theRowMapper) {
            super(theRowMapper);
        }

        /**
         * Executes the SQL query and returns a list of mapped result
         * from the ResultSet.
         *
         * @param connection the database connection used to execute the query
         * @return the list of mapped result, or empty if no result is found
         * @throws SQLException if a database access error occurs
         */
        @Override
        public List<T> execute(final Connection connection)
                throws SQLException {
            List<T> result = new ArrayList<>();
            try (PreparedStatement preparedStatement
                         = connection.prepareStatement(sql
                    , java.sql.Statement.RETURN_GENERATED_KEYS)) {
                prepare(preparedStatement);
                preparedStatement.executeUpdate();
                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    while (resultSet.next()) {
                        result.add(mapRow(resultSet));
                    }
                }
            }
            return result;
        }
    }   
    /**
     * Prepares the PreparedStatement by binding all the parameters
     * to their respective
     * positions in the SQL query.
     *
     * @param preparedStatement the PreparedStatement to bind parameters to
     * @throws SQLException if a database access error occurs during parameter
     * binding
     */
    private void prepare(final PreparedStatement preparedStatement)
            throws SQLException {
        for (ParamMapper<?> paramMapper: paramMappers) {
            paramMapper.mapParam(preparedStatement);
        }
    }
}

    /**
     * Begins new Transaction.
     * @return transaction
     */
    public Transaction begin() {
        return new Transaction();
    }

    public final class Transaction {

        private final List<Sql<Integer>> statements;

        public Transaction() {
            statements = new ArrayList<>();
        }

        public void commit(final DataSource dbDataSource) throws SQLException {
            final Connection connection = dbDataSource.getConnection();
            try {
                connection.setAutoCommit(false);
                for(Sql<Integer> statement:statements) {
                    statement.execute(connection);
                }
                connection.commit();
            } catch (SQLException sqlException) {
                connection.rollback();
                throw sqlException;
            } finally {
                if(connection != null) {
                    connection.close();
                }
            }
        }

        public Transaction perform(Sql<Integer> statement) {
            statements.add(statement);
            return this;
        }
        
    }
    public static final class SelectQuery<T extends Value<?, ?>, V>  {

        private final String sql;
        private final SqlBuilder.RowMapper<V> rowMapper;

        private final List<T> values;

        public SelectQuery(final String sql,final SqlBuilder.RowMapper<V> rowMapper) {
            this.sql = sql;
            this.rowMapper = rowMapper;
            this.values = new ArrayList<>();
        }


        public SelectQuery<T, V> param(final T value) {
            this.values.add(value);
            return this;
        }

        public Optional<V> optional(final DataSource dataSource) throws SQLException {
            DataManager.SqlBuilder sqlBuilder = dataManager.sql(sql);

            for (T value:values) {
                value.set(sqlBuilder);
            }
            
            return Optional.ofNullable(sqlBuilder.queryForOne(this.rowMapper).execute(dataSource));
        }

        public List<V> list(final DataSource dataSource) throws SQLException {
            DataManager.SqlBuilder sqlBuilder = dataManager.sql(sql);

            for (T value:values) {
                value.set(sqlBuilder);
            }
            
            return sqlBuilder.queryForList(this.rowMapper).execute(dataSource);
        }
    }

    public static final class Statement<T extends Value<?, ?>>  implements Sql<Integer>{

    private final String sql;
    private final List<T> values;

    public Statement(final String sql) {
        this.sql = sql;
        this.values = new ArrayList<>();
    }


    public Statement<T> param(final T value) {
        this.values.add(value);
        return this;
    }

    @Override
    public Integer execute(final Connection connection) throws SQLException {
        DataManager.SqlBuilder sqlBuilder = dataManager.sql(sql);

        for (Value<?, ?> value : values) {
            value.set(sqlBuilder);
        }

        return sqlBuilder.execute(connection);
    }


}

/**
* Class for building the SQL WhereClause.
*/
public interface WhereClause {
    String asSql() ;
}   



    public static final class DeleteStatement implements DataManager.Sql<Integer> {

        private final String sql;

        public DeleteStatement(final String sql) {
            this.sql = sql;
        }

        @Override
        public Integer execute(final Connection connection) throws SQLException  {
            return dataManager.sql(this.sql)
                    .execute(connection);
        }

        public DataManager.Sql<Integer> where(final WhereClause whereClause) {
            final String query = this.sql
                    + ( whereClause == null ? "" : (" WHERE " + whereClause.asSql()) );
            return dataManager.sql(query);
        }

        public DataManager.Statement<Value<?,?>> sql(final String sql) {
            return new DataManager.Statement<>(sql);
        }

    }

}


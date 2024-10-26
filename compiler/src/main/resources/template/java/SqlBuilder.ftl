/**
 * SqlBuilder is a utility class that simplifies the process of constructing
 * SQL queries with dynamic parameters and executing them. It helps developers
 * manage SQL queries more efficiently with less boilerplate code, supporting
 * both query execution and parameterized updates.
 */
public class SqlBuilder {

    private final String sql;         // The SQL query to be executed.
    private final List<Value<?,?>> parameters;  // A list of parameters for the query.

    /**
     * Constructor that initializes the SqlBuilder with a given SQL query.
     *
     * @param sql the SQL query to be prepared and executed
     */
    public SqlBuilder(final String sql) {
        this.sql = sql;
        this.parameters = new ArrayList<>();
    }

    /**
     * Adds a parameter to the SQL query. The method allows chaining and is used
     * to bind values to placeholders in the SQL query.
     *
     * @param value the value of the parameter to be added
     * @return the current SqlBuilder instance, for method chaining
     */
    public SqlBuilder param(Value<?,?> value) {
        this.parameters.add(value);
        return this;
    }

    /**
     * Creates new Statement.
     * @return statement
     */
    public Statement prepare() {
        return new Statement();
    }

    /**
     * Creates SQL Statement for Database.
     */
    public final class Statement {
        /**
         * Executes an update (such as INSERT, UPDATE, DELETE) using the prepared SQL query
         * and the bound parameters.
         *
         * @return the number of rows affected by the update
         * @throws SQLException if a database access error occurs
         */
        public int executeUpdate() throws SQLException {
            int updtedRows;
            try (Connection connection = DataManager.this.dbDataSource.getConnection()){
                updtedRows = executeUpdate(connection);
            }
            return updtedRows;
        }

        /**
         * Executes an update (such as INSERT, UPDATE, DELETE) using the prepared SQL query
         * and the bound parameters.
         *
         * @param connection the database connection used to execute the query
         * @return the number of rows affected by the update
         * @throws SQLException if a database access error occurs
         */
        public int executeUpdate(final Connection connection) throws SQLException {
            int updatedRows;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                prepare(preparedStatement);
                updatedRows = preparedStatement.executeUpdate();
            }
            return updatedRows;
        }
    }

    /**
     * Creates a new Query object that can be used to execute a SELECT query and map
     * the result set to a specific object type using the provided RowMapper.
     *
     * @param <T> the type of object to map the result set to
     * @param rowMapper an implementation of RowMapper to map each row of the result set
     * @return a new Query instance for execution
     */
    public <T> SqlBuilder.Query<T> query(RowMapper<T> rowMapper) {
        return this.new Query<>(rowMapper);
    }

        /**
     * Checks if result set exists.
     * @return exits
     */
    public boolean exists() throws SQLException {
        boolean isExists = false;
        try (Connection connection = DataManager.this.dbDataSource.getConnection()){
            isExists = exists(connection);
        }
        return isExists;
    }

    /**
     * Checks if result set exists.
     * @return exits
     */
    public boolean exists(final Connection connection) throws SQLException {
        boolean isExists = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            prepare(preparedStatement);
            isExists = preparedStatement.executeQuery().next();
        }
        return isExists;
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
     * The Query class encapsulates the logic for executing SELECT queries and mapping
     * the results to Java objects using a RowMapper.
     *
     * @param <T> the type of object to map the result set to
     */
    public class Query<T> {

        private final RowMapper<T> rowMapper;

        /**
         * Private constructor for creating a Query instance with the specified RowMapper.
         *
         * @param rowMapper the RowMapper used to map the ResultSet rows
         */
        private Query(final RowMapper<T> rowMapper) {
            this.rowMapper = rowMapper;
        }

        /**
         * Executes the SQL query and returns a single mapped result from the ResultSet.
         *
         * @return the single mapped result, or null if no result is found
         * @throws SQLException if a database access error occurs
         */
        public T single() throws SQLException {
            T result;
            try (Connection connection = DataManager.this.dbDataSource.getConnection()) {
                result = single(connection);
            }
            return result;
        }

        /**
         * Executes the SQL query and returns a single mapped result from the ResultSet.
         *
         * @param connection the database connection used to execute the query
         * @return the single mapped result, or null if no result is found
         * @throws SQLException if a database access error occurs
         */
        public T single(final Connection connection) throws SQLException {
            T result = null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                prepare(preparedStatement);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    result = rowMapper.mapRow(resultSet);
                }
            }
            return result;
        }

        /**
         * Executes the SQL query and returns a list of mapped result from the ResultSet.
         *
         * @return the list of mapped result, or empty if no result is found
         * @throws SQLException if a database access error occurs
         */
        public List<T> list() throws SQLException {
            List<T> result;
            try (Connection connection = DataManager.this.dbDataSource.getConnection()) {
                result = list(connection);
            }
            return result;
        }
        
        /**
         * Executes the SQL query and returns a list of mapped result from the ResultSet.
         *
         * @param connection the database connection used to execute the query
         * @return the list of mapped result, or empty if no result is found
         * @throws SQLException if a database access error occurs
         */
        public List<T> list(final Connection connection) throws SQLException {
            List<T> result = new ArrayList<>();
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                prepare(preparedStatement);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    result.add(rowMapper.mapRow(resultSet));
                }
            }
            return result;
        }



    }

    /**
    * Gets Integer from SQL.
    *
    * @return result
    * @throws SQLException
    */
    public int getInt() throws SQLException {
        int count = 0;
        try (Connection connection = DataManager.this.dbDataSource.getConnection()) {
            count = getInt(connection);
        }
        return count;
    }

    /**
    * Gets Integer from SQL.
    *
    * @param connection the database connection used to execute the query
    * @return result
    * @throws SQLException
    */
    private int getInt(final Connection connection) throws SQLException {
        int count = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }

        }
        return count;
    }

    /**
     * Prepares the PreparedStatement by binding all the parameters to their respective
     * positions in the SQL query.
     *
     * @param preparedStatement the PreparedStatement to bind parameters to
     * @throws SQLException if a database access error occurs during parameter binding
     */
    private void prepare(final PreparedStatement preparedStatement) throws SQLException {
        for (int i = 0; i < parameters.size(); i++) {
            parameters.get(i).set(preparedStatement,i+1);
        }
    }
}

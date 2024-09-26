/**
 * Row Mapper for Resultsets.
 */
public static interface RowMapper<T> {
    T mapRow(ResultSet resultSet, int i) throws SQLException;
}

public static class SqlBuilder {

    private final String sql;

    private final List<Object> params ;

    public SqlBuilder(final String sql) {
        this.sql = sql;
        params = new ArrayList<>(5);
    }

    public static SqlBuilder sql(final String sql) {
        return new SqlBuilder(sql);
    }

    public SqlBuilder param(final Object value) {
        params.add(value);
        return this;
    }

    public  <T> SqlBuilder.Query<T> query(RowMapper<T> rowMapper) {
        return this.new Query<T>(rowMapper);
    }

    public class Query<T> {

        private final RowMapper<T> rowMapper;

        private Query(final RowMapper<T> rowMapper) {
            this.rowMapper = rowMapper;
        }
        
        public T single(final Connection connection) throws SQLException {
            T record = null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                
                for (int i = 0; i < SqlBuilder.this.params.size(); i++) {
                    preparedStatement.setObject(i+1, SqlBuilder.this.params.get(i));
                }
                
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) record = this.rowMapper.mapRow(resultSet, 1);
            }
            return record;
        }
    }
    
}

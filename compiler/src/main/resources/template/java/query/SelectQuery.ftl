    public static final class SelectQuery<T extends Value<?, ?>, V>  {

        private final String sql;
        private final RowMapper<V> rowMapper;

        private final List<T> values;

        public SelectQuery(final String sql,final RowMapper<V> rowMapper) {
            this.sql = sql;
            this.rowMapper = rowMapper;
            this.values = new ArrayList<>();
        }


        public SelectQuery<T, V> param(final T value) {
            this.values.add(value);
            return this;
        }

        public Optional<V> optional(final DataSource dataSource) throws SQLException {
            SqlBuilder.PreparedSqlBuilder sqlBuilder = SqlBuilder.prepareSql(sql);

            for (T value:values) {
                value.set(sqlBuilder);
            }
            
            return Optional.ofNullable(sqlBuilder.queryForOne(rowMapper).execute(dataSource));
        }

        public List<V> list(final DataSource dataSource) throws SQLException {
            SqlBuilder.PreparedSqlBuilder sqlBuilder = SqlBuilder.prepareSql(sql);

            for (T value:values) {
                value.set(sqlBuilder);
            }
            
            return sqlBuilder.queryForList(rowMapper).execute(dataSource);
        }
    }

    <#assign a=addImportStatement("java.util.Optional")>

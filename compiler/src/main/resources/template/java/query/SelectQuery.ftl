    public static final class SelectQuery<T extends Value<?, ?>, V>  {

        private final String sql;
        private final RowMapper<V> rowMapper;
        private final DataSource defaultDataSource;

        private final List<T> values;

        public SelectQuery(final String sql, final RowMapper<V> rowMapper) {
            this(sql, rowMapper, null);
        }

        public SelectQuery(final String sql, final RowMapper<V> rowMapper, final DataSource defaultDataSource) {
            this.sql = sql;
            this.rowMapper = rowMapper;
            this.defaultDataSource = defaultDataSource;
            this.values = new ArrayList<>();
        }


        public SelectQuery<T, V> param(final T value) {
            this.values.add(value);
            return this;
        }

        public Optional<V> optional() throws SQLException {
            if (this.defaultDataSource == null) {
                throw new IllegalStateException("Default DataSource is not configured for SelectQuery. Pass DataSource to optional(dataSource) or construct SelectQuery with DataSource.");
            }
            return optional(this.defaultDataSource);
        }

        public Optional<V> optional(final DataSource dataSource) throws SQLException {
            SqlBuilder.PreparedSqlBuilder sqlBuilder = SqlBuilder.prepareSql(sql);

            for (T value:values) {
                value.set(sqlBuilder);
            }
            
            return Optional.ofNullable(sqlBuilder.queryForOne(rowMapper).execute(dataSource));
        }

        public List<V> list() throws SQLException {
            if (this.defaultDataSource == null) {
                throw new IllegalStateException("Default DataSource is not configured for SelectQuery. Pass DataSource to list(dataSource) or construct SelectQuery with DataSource.");
            }
            return list(this.defaultDataSource);
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



    public static final class DeleteStatement implements Sql<Integer> {

        private final String sql;
        private final DataSource defaultDataSource;

        public DeleteStatement(final String sql) {
            this(sql, null);
        }

        public DeleteStatement(final String sql, final DataSource defaultDataSource) {
            this.sql = sql;
            this.defaultDataSource = defaultDataSource;
        }

        public Integer execute() throws SQLException {
            if (this.defaultDataSource == null) {
                throw new IllegalStateException("Default DataSource is not configured in DataManager. Pass DataSource to execute(dataSource) or configure DataManager.getManager(dataSource, ...)");
            }
            return execute(this.defaultDataSource);
        }

        @Override
        public Integer execute(final Connection connection) throws SQLException  {
            return SqlBuilder.sql(this.sql)
                    .execute(connection);
        }

        public DeleteStatement where(final WhereClause whereClause) {
            final String query = this.sql
                    + ( whereClause == null ? "" : (" WHERE " + whereClause.asSql()) );
            return new DeleteStatement(query, this.defaultDataSource);
        }

        public DataManager.Statement<Value<?,?>> sql(final String sql) {
            return new DataManager.Statement<>(sql);
        }

    }

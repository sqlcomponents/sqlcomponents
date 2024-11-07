


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

        public DataManager.Sql<Integer> where(final WhereClause<?> whereClause) {
            final String query = this.sql
                    + ( whereClause == null ? "" : (" WHERE " + whereClause.asSql()) );
            return dataManager.sql(query);
        }

        public DataManager.Statement<Value<?,?>> sql(final String sql) {
            return new DataManager.Statement<>(sql);
        }

    }

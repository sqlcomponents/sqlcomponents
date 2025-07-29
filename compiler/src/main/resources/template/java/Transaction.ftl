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
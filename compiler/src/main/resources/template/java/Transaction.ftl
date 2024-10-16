    public final class Transaction {

        private final List<SqlBuilder.Statement> statements;

        public Transaction() {
            statements = new ArrayList<>();
        }

        public void commit() throws SQLException {
            final Connection connection = DataManager.this.dbDataSource.getConnection();
            try {
                connection.setAutoCommit(false);
                for(SqlBuilder.Statement statement:statements) {
                    statement.executeUpdate(connection);
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

        public Transaction perform(SqlBuilder.Statement statement) {
            statements.add(statement);
            return this;
        }
        
    }
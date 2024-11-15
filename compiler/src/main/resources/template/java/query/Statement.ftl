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

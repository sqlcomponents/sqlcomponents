public static final class DeleteQuery<T extends Value<?, ?>> {

    private final String sql;
    private final List<T> values;

    public DeleteQuery(final String sql) {
        this.sql = sql;
        this.values = new ArrayList<>();
    }


    public DeleteQuery<T> param(final T value) {
        this.values.add(value);
        return this;
    }

    public int execute(final DataSource dataSource) throws SQLException {
        DataManager.SqlBuilder sqlBuilder = dataManager.sql(sql);

        for (Value<?, ?> value : values) {
            value.set(sqlBuilder);
        }

        return sqlBuilder.execute(dataSource);
    }


}

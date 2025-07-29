<#macro LocalDateTimeColumn property>
    <@columnheader property=property/>

    public void set(final DataManager.SqlBuilder preparedStatement, final LocalDateTime value) {
    preparedStatement.param(value == null ? null : java.sql.Timestamp.valueOf(value));
    }

    @Override
    public LocalDateTime get(final ResultSet resultSet, final int i) throws SQLException {
        java.sql.Timestamp timestamp = resultSet.getTimestamp(i);
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }

    public final WhereClause  eq(final LocalDateTime value) {
    sql = "${property.column.escapedName?j_string} =" + value;
    return getWhereClause();
    }

    public final WhereClause  gt(final LocalDateTime value) {
    sql = "${property.column.escapedName?j_string} >" + value;
    return getWhereClause();
    }

    public final WhereClause  gte(final LocalDateTime value) {
    sql = "${property.column.escapedName?j_string} >=" + value;
    return getWhereClause();
    }

    public final WhereClause  lt(final LocalDateTime value) {
    sql = "${property.column.escapedName?j_string} <" + value;
    return getWhereClause();
    }

    public final WhereClause  lte(final LocalDateTime value) {
    sql = "${property.column.escapedName?j_string} <=" + value;
    return getWhereClause();
    }

    <@columnfooter property=property/>
</#macro>
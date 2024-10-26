<#macro LocalDateColumn property>
    <@columnheader property=property/>

    public void set(final DataManager.SqlBuilder preparedStatement, final LocalDate value) {
    preparedStatement.param(value == null ? null : java.sql.Date.valueOf(value));
    }

    @Override
    public LocalDate get(final ResultSet resultSet, final int i) throws SQLException {
        return resultSet.getDate(i) == null ? null : resultSet.getDate(i).toLocalDate();
    }

    public final WhereClause  eq(final LocalDate value) {
    sql = "${property.column.escapedName?j_string} =" + value;
    return getWhereClause();
    }

    public final WhereClause  gt(final LocalDate value) {
    sql = "${property.column.escapedName?j_string} >" + value;
    return getWhereClause();
    }

    public final WhereClause  gte(final LocalDate value) {
    sql = "${property.column.escapedName?j_string} >=" + value;
    return getWhereClause();
    }

    public final WhereClause  lt(final LocalDate value) {
    sql = "${property.column.escapedName?j_string} <" + value;
    return getWhereClause();
    }

    public final WhereClause  lte(final LocalDate value) {
    sql = "${property.column.escapedName?j_string} <=" + value;
    return getWhereClause();
    }

    <@columnfooter property=property/>
</#macro>
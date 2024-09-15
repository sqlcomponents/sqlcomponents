<#macro LocalTimeColumn property>
    <@columnheader property=property/>

    public void set(final PreparedStatement preparedStatement, final int i, final LocalTime value) throws SQLException {
    preparedStatement.setTime(i,value == null ? null : java.sql.Time.valueOf(value));
    }

    @Override
    public LocalTime get(final ResultSet resultSet, final int i) throws SQLException {
        return resultSet.getTime(i) == null ? null : resultSet.getTime(i).toLocalTime();
    }
    public final WhereClause  eq(final LocalTime value) {
    sql = "${property.column.escapedName?j_string} =" + value;
    return getWhereClause();
    }

    public final WhereClause  gt(final LocalTime value) {
    sql = "${property.column.escapedName?j_string} >" + value;
    return getWhereClause();
    }

    public final WhereClause  gte(final LocalTime value) {
    sql = "${property.column.escapedName?j_string} >=" + value;
    return getWhereClause();
    }

    public final WhereClause  lt(final LocalTime value) {
    sql = "${property.column.escapedName?j_string} <" + value;
    return getWhereClause();
    }

    public final WhereClause  lte(final LocalTime value) {
    sql = "${property.column.escapedName?j_string} <=" + value;
    return getWhereClause();
    }


    <@columnfooter property=property/>
</#macro>
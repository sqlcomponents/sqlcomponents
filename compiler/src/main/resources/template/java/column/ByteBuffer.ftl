<#macro ByteBuffer property>
    <@columnheader property=property/>
    public void set(final DataManager.SqlBuilder preparedStatement, final ByteBuffer value) {
    preparedStatement.param(value == null ? null : value.array());
    }
    @Override
    public ByteBuffer get(final ResultSet resultSet, final int i) throws SQLException {
        return resultSet.getBytes(i) == null ? null : ByteBuffer.wrap(resultSet.getBytes(i));
    }
    public final WhereClause  eq(final String value) {
    sql = "${property.column.escapedName?j_string} ='" + value + "'";
    return getWhereClause();
    }
    <@columnfooter property=property/>
</#macro>
<#macro UUIDColumn property>
    <@columnheader property=property/>
    public void set(final PreparedStatement preparedStatement, final int i, final UUID uuid) throws SQLException {
    preparedStatement.setObject(i,(uuid == null) ? null : uuid.toString(), java.sql.Types.OTHER);
    }

    public UUID get(final ResultSet rs,final int index) throws SQLException {
        return (UUID) rs.getObject(index);
    }

    public final WhereClause  eq(final UUID value) {
    sql = "${property.column.escapedName?j_string} ='" + value.toString() + "'";
    return getWhereClause();
    }
    <@columnfooter property=property/>
</#macro>
<#macro UUIDColumn property>
    <@columnheader property=property/>
    public void set(final DataManager.SqlBuilder preparedStatement, final UUID uuid) {
    preparedStatement.param((uuid == null) ? null : uuid.toString(), java.sql.Types.OTHER);
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
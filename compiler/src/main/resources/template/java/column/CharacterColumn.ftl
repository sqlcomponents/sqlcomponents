<#macro CharacterColumn property>
    <@columnheader property=property/>
    
    @Override
    public void set(final DataManager.SqlBuilder preparedStatement, final Character value) {
    preparedStatement.param(value == null ? null : String.valueOf(value));
    }

    @Override
    public Character get(final ResultSet resultSet, final int i) throws SQLException {
        String charString = resultSet.getString(i);
        return charString == null ? null : charString.charAt(0);
    }

    public final WhereClause  eq(final String value) {
    sql = "${property.column.escapedName?j_string} ='" + value + "'";
    return getWhereClause();
    }

    public final WhereClause LIKE(final String value) {
    sql = "${property.column.escapedName?j_string} LIKE '" + value + "'";
    return getWhereClause();
    }
    <@columnfooter property=property/>
</#macro>

<#macro StringColumn property>
    <@columnheader property=property/>

    public void set(final PreparedStatement preparedStatement, final int i, final String value) throws SQLException {
    <#if property.column.typeName == "macaddr8" >
    PGobject pgObject = new PGobject();
     pgObject.setType("macaddr8");
    pgObject.setValue(value);
    preparedStatement.setObject(i, pgObject);
     <#elseif property.column.typeName == "macaddr" >
    PGobject pgObject = new PGobject();
     pgObject.setType("macaddr");
    pgObject.setValue(value);
    preparedStatement.setObject(i, pgObject);
   
    <#elseif property.column.typeName == "path" >
    PGobject pgObject = new PGobject();
     pgObject.setType("path");
    pgObject.setValue(value);
    preparedStatement.setObject(i, pgObject);
    <#else>
    preparedStatement.setString(i,value);
    </#if>
    }

    @Override
    public String get(final ResultSet resultSet, final int i) throws SQLException {
        return resultSet.getString(i);
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
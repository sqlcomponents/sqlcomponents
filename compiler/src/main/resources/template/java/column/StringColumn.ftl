<#macro StringColumn property>
    <@columnheader property=property/>

    public void set(final DataManager.SqlBuilder preparedStatement, final String value) {
    <#if property.column.typeName == "macaddr8" >
    PGobject pgObject = new PGobject();
     pgObject.setType("macaddr8");
    try {
            pgObject.setValue(value);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    preparedStatement.param( pgObject);
     <#elseif property.column.typeName == "macaddr" >
    PGobject pgObject = new PGobject();
     pgObject.setType("macaddr");
    try {
            pgObject.setValue(value);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    preparedStatement.param( pgObject);
   
    <#elseif property.column.typeName == "path" >
    PGobject pgObject = new PGobject();
     pgObject.setType("path");
    try {
            pgObject.setValue(value);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    preparedStatement.param( pgObject);
    <#else>
    preparedStatement.param(value);
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
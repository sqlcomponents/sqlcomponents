<#macro BooleanColumn property>
    <@columnheader property=property/>
    public void set(final PreparedStatement preparedStatement, final int i, final Boolean value) throws SQLException {
    <#if property.column.typeName == "bit" >
        if(value == null) {
            preparedStatement.setNull(i,${property.column.dataType},"${property.column.typeName}" );
        } else {
        PGobject bitObject = new PGobject();
        bitObject.setType("bit");
        bitObject.setValue(!value ? "0" : "1" );
        preparedStatement.setObject(i, bitObject);
        }
    <#else>
        if(value == null) {
            preparedStatement.setNull(i,${property.column.dataType},"${property.column.typeName}" );
        } else {
            preparedStatement.setBoolean(i,value);
        }
    </#if>
    
    }

    @Override
    public Boolean get(final ResultSet resultSet, final int i) throws SQLException {
        return resultSet.getBoolean(i);
    }

    public final WhereClause  eq(final Boolean value) {
    sql = "${property.column.escapedName?j_string} =" + value ;
    return getWhereClause();
    }
    <@columnfooter property=property/>
</#macro>
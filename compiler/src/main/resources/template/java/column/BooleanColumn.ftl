<#macro BooleanColumn property>
    <@columnheader property=property/>
    public void set(final DataManager.SqlBuilder preparedStatement, final Boolean value) {
    <#if property.column.typeName == "bit" >
        if(value == null) {
            preparedStatement.paramNull(${property.column.dataType},"${property.column.typeName}" );
        } else {
        PGobject bitObject = new PGobject();
        bitObject.setType("bit");
        bitObject.setValue(!value ? "0" : "1" );
        preparedStatement.param( bitObject);
        }
    <#else>
        if(value == null) {
            preparedStatement.paramNull(${property.column.dataType},"${property.column.typeName}" );
        } else {
            preparedStatement.param(value);
        }
    </#if>
    
    }

    @Override
    public Boolean get(final ResultSet resultSet, final int i) throws SQLException {
        return resultSet.getObject(i, Boolean.class);
    }

    public final WhereClause  eq(final Boolean value) {
    sql = "${property.column.escapedName?j_string} =" + value ;
    return getWhereClause();
    }
    <@columnfooter property=property/>
</#macro>
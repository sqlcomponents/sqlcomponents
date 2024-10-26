<#macro numbercolumn type property>

    public void set(final DataManager.SqlBuilder preparedStatement, final ${type} value) {
    if(value == null) {
        preparedStatement.paramNull(${property.column.dataType},"${property.column.typeName}" );
    } else {
        preparedStatement.param(value);
    }
        

    
    }

    @Override
    public ${type} get(final ResultSet resultSet, final int i) throws SQLException {
        return resultSet.getObject(i) == null ? null : resultSet.get${type}(i);
    }

    public final WhereClause eq(final ${type} value) {
    sql = "${property.column.escapedName?j_string} =" + value;
    return getWhereClause();
    }

    public final WhereClause gt(final ${type} value) {
    sql = "${property.column.escapedName?j_string} >" + value;
    return getWhereClause();
    }

    public final WhereClause  gte(final ${type} value) {
    sql = "${property.column.escapedName?j_string} >=" + value;
    return getWhereClause();
    }

    public final WhereClause  lt(final ${type} value) {
    sql = "${property.column.escapedName?j_string} <" + value;
    return getWhereClause();
    }

    public final WhereClause  lte(final ${type} value) {
    sql = "${property.column.escapedName?j_string} <=" + value;
    return getWhereClause();
    }



</#macro>

<#macro LongColumn property>
    <@columnheader property=property/>

    <@numbercolumn type="Long" property=property/>

    <@columnfooter property=property/>
</#macro>

<#macro ShortColumn property>
    <@columnheader property=property/>

    <@numbercolumn type="Short" property=property/>

    <@columnfooter property=property/>
</#macro>

<#macro IntegerColumn property>
    <@columnheader property=property/>

    <@numbercolumn type="Integer" property=property/>

    <@columnfooter property=property/>
</#macro>

<#macro ByteColumn property>
    <@columnheader property=property/>

    <@numbercolumn type="Byte" property=property/>

    <@columnfooter property=property/>
</#macro>
<#macro DoubleColumn property>
    <@columnheader property=property/>

    <@numbercolumn type="Double" property=property/>

    <@columnfooter property=property/>
</#macro>
<#macro BigDecimalColumn property>
    <@columnheader property=property/>

    <@numbercolumn type="BigDecimal" property=property/>

    <@columnfooter property=property/>
</#macro>

<#macro FloatColumn property>
    <@columnheader property=property/>
    <@numbercolumn type="Float" property=property/>
    <@columnfooter property=property/>
</#macro>
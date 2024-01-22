<#include "base.ftl">
<#macro columnheader property>
public static class ${property.name?cap_first}Column extends Column<${getClassName(property.dataType)}> {
    private String sql;

    public ${property.name?cap_first}Column(final PartialWhereClause  whereClause) {
        super(whereClause);
    }

    public String name() {
        return "${property.column.escapedName?j_string}";
    }

    <@ColumnFoundation property=property/>

</#macro>

<#macro columnfooter property>
    @Override
    public String asSql() {
        return sql;
    }

    public boolean validate(${getClassName(property.dataType)} value) {
        return true;
    }
    
}
</#macro>

<#macro ColumnFoundation property>
            public final WhereClause isNull() {
                sql = "${property.column.escapedName?j_string} IS NULL";
                return getWhereClause();
            }

            public final WhereClause isNotNull() {
                sql = "${property.column.escapedName?j_string} IS NOT NULL";
                return getWhereClause();
            }
</#macro>

<#macro StringColumn property>
<@columnheader property=property/>

    public void set(final PreparedStatement preparedStatement, final int i, final String value) throws SQLException {
        preparedStatement.setString(i,value);
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

<#macro UUIDColumn property>
<@columnheader property=property/>
    public void set(final PreparedStatement preparedStatement, final int i, final UUID value) throws SQLException {
        preparedStatement.setObject(i,convertUuid(value), java.sql.Types.OTHER);
    }

    public final WhereClause  eq(final UUID value) {
        sql = "${property.column.escapedName?j_string} ='" + value.toString() + "'";
        return getWhereClause();
    }
<@columnfooter property=property/>
</#macro>

<#macro DurationColumn property>
<@columnheader property=property/>
    public void set(final PreparedStatement preparedStatement, final int i, final Duration value) throws SQLException {
        preparedStatement.setObject(i,convertInterval(value));
    }
    public final WhereClause  eq(final String value) {
        sql = "${property.column.escapedName?j_string} ='" + value + "'";
        return getWhereClause();
    }
<@columnfooter property=property/>
</#macro>

<#macro ByteBuffer property>
<@columnheader property=property/>
    public void set(final PreparedStatement preparedStatement, final int i, final ByteBuffer value) throws SQLException {
        preparedStatement.setBytes(i,value == null ? null : value.array());
    }
    public final WhereClause  eq(final String value) {
        sql = "${property.column.escapedName?j_string} ='" + value + "'";
        return getWhereClause();
    }
<@columnfooter property=property/>
</#macro>

<#macro JsonNodeColumn property>
<@columnheader property=property/>

    public void set(final PreparedStatement preparedStatement, final int i, final  JsonNode value) throws SQLException {
        preparedStatement.setObject(i,convertJson(value), java.sql.Types.OTHER);
    }

    public final WhereClause  eq(final String value) {
        sql = "${property.column.escapedName?j_string} ='" + value + "'";
        return getWhereClause();
    }
<@columnfooter property=property/>
</#macro>

<#macro CharacterColumn property>
<@columnheader property=property/>
    public void set(final PreparedStatement preparedStatement, final int i, final Character value) throws SQLException {
        preparedStatement.setString(i,value == null ? null : String.valueOf(value));
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


<#macro BooleanColumn property>
<@columnheader property=property/>
    public void set(final PreparedStatement preparedStatement, final int i, final Boolean value) throws SQLException {
        preparedStatement.setBoolean(i,value);
    }
    public final WhereClause  eq(final Boolean value) {
        sql = "${property.column.escapedName?j_string} =" + value ;
        return getWhereClause();
    }
<@columnfooter property=property/>
</#macro>

<#macro numbercolumn type property>

    public void set(final PreparedStatement preparedStatement, final int i, final ${type} value) throws SQLException {
        preparedStatement.set${type}(i,value);
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


<#macro LocalDateColumn property>
<@columnheader property=property/>

    public void set(final PreparedStatement preparedStatement, final int i, final LocalDate value) throws SQLException {
        preparedStatement.setDate(i,value == null ? null : java.sql.Date.valueOf(value));
    }

    public final WhereClause  eq(final LocalDate value) {
        sql = "${property.column.escapedName?j_string} =" + value;
        return getWhereClause();
    }

    public final WhereClause  gt(final LocalDate value) {
        sql = "${property.column.escapedName?j_string} >" + value;
        return getWhereClause();
    }

    public final WhereClause  gte(final LocalDate value) {
        sql = "${property.column.escapedName?j_string} >=" + value;
        return getWhereClause();
    }

    public final WhereClause  lt(final LocalDate value) {
        sql = "${property.column.escapedName?j_string} <" + value;
        return getWhereClause();
    }

    public final WhereClause  lte(final LocalDate value) {
        sql = "${property.column.escapedName?j_string} <=" + value;
        return getWhereClause();
    }

<@columnfooter property=property/>
</#macro>

<#macro LocalTimeColumn property>
<@columnheader property=property/>

    public void set(final PreparedStatement preparedStatement, final int i, final LocalTime value) throws SQLException {
        preparedStatement.setTime(i,value == null ? null : java.sql.Time.valueOf(value));
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

<#macro LocalDateTimeColumn property>
<@columnheader property=property/>

    public void set(final PreparedStatement preparedStatement, final int i, final LocalDateTime value) throws SQLException {
        preparedStatement.setTimestamp(i,value == null ? null : java.sql.Timestamp.valueOf(value));
    }

    public final WhereClause  eq(final LocalDateTime value) {
        sql = "${property.column.escapedName?j_string} =" + value;
        return getWhereClause();
    }

    public final WhereClause  gt(final LocalDateTime value) {
        sql = "${property.column.escapedName?j_string} >" + value;
        return getWhereClause();
    }

    public final WhereClause  gte(final LocalDateTime value) {
        sql = "${property.column.escapedName?j_string} >=" + value;
        return getWhereClause();
    }

    public final WhereClause  lt(final LocalDateTime value) {
        sql = "${property.column.escapedName?j_string} <" + value;
        return getWhereClause();
    }

    public final WhereClause  lte(final LocalDateTime value) {
        sql = "${property.column.escapedName?j_string} <=" + value;
        return getWhereClause();
    }

<@columnfooter property=property/>
</#macro>

<#macro PointColumn property>
<@columnheader property=property/>

    public void set(final PreparedStatement preparedStatement, final int i, final Point value) throws SQLException {
        preparedStatement.setObject(i,convertPoint(value), java.sql.Types.OTHER);
    }
    
<@columnfooter property=property/>
</#macro>

<#macro LineSegmentColumn property>
<@columnheader property=property/>
<#assign a=addImportStatement("org.locationtech.jts.geom.LineSegment")>
<#assign a=addImportStatement("org.postgresql.geometric.PGlseg")>
public void set(final PreparedStatement preparedStatement,final int i, final LineSegment value) throws SQLException {
   preparedStatement.setObject(i,value == null ? null : new PGlseg(value.minX(),value.minY(),value.maxX(),value.maxY()));
}
<@columnfooter property=property/>
</#macro>

<#macro InetColumn property>
    <@columnheader property=property/>
    <#assign a=addImportStatement("java.net.InetAddress")>
    public void set(final PreparedStatement preparedStatement, final int i, final InetAddress value) throws SQLException {
    preparedStatement.setObject(i, value == null ? null : value.getHostAddress(), java.sql.Types.OTHER);
    }
    <@columnfooter property=property/>
</#macro>
<#include "base.ftl">

<#include "column/BitSetColumn.ftl">
<#include "column/BooleanColumn.ftl">
<#include "column/BoxColumn.ftl">
<#include "column/ByteBuffer.ftl">
<#include "column/CharacterColumn.ftl">
<#include "column/CidrColumn.ftl">
<#include "column/CircleColumn.ftl">
<#include "column/DurationColumn.ftl">
<#include "column/InetAddressColumn.ftl">
<#include "column/JsonNodeColumn.ftl">
<#include "column/LineColumn.ftl">
<#include "column/LineSegmentColumn.ftl">
<#include "column/LocalDateColumn.ftl">
<#include "column/LocalDateTimeColumn.ftl">
<#include "column/LocalTimeColumn.ftl">
<#include "column/NumberColumn.ftl">
<#include "column/PointColumn.ftl">
<#include "column/PolygonColumn.ftl">
<#include "column/StringColumn.ftl">
<#include "column/UUIDColumn.ftl">


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
<#include "base.ftl">
<#macro columnheader property>
public static class ${property.name?cap_first}Column extends Column<${getClassName(property.dataType)}> {
    private String sql;

    public ${property.name?cap_first}Column(final PartialWhereClause  whereClause) {
        super(whereClause);
    }
</#macro>

<#macro columnfooter property>
    @Override
    protected String asSql() {
        return sql;
    }

    protected Boolean validate(${getClassName(property.dataType)} value) {
        return true;
    }
}
</#macro>


<#macro StringColumn property>
<@columnheader property=property/>
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
    public final WhereClause  eq(final String value) {
        sql = "${property.column.escapedName?j_string} ='" + value + "'";
        return getWhereClause();
    }
<@columnfooter property=property/>
</#macro>

<#macro DurationColumn property>
<@columnheader property=property/>
    public final WhereClause  eq(final String value) {
        sql = "${property.column.escapedName?j_string} ='" + value + "'";
        return getWhereClause();
    }
<@columnfooter property=property/>
</#macro>

<#macro JSONObjectColumn property>
<@columnheader property=property/>
    public final WhereClause  eq(final String value) {
        sql = "${property.column.escapedName?j_string} ='" + value + "'";
        return getWhereClause();
    }
<@columnfooter property=property/>
</#macro>

<#macro CharacterColumn property>
<@columnheader property=property/>
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
    public final WhereClause  eq(final Boolean value) {
        sql = "${property.column.escapedName?j_string} =" + value ;
        return getWhereClause();
    }
<@columnfooter property=property/>
</#macro>

<#macro numbercolumn type property>
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

<#macro FloatColumn property>
<@columnheader property=property/>
  <@numbercolumn type="Float" property=property/>
<@columnfooter property=property/>
</#macro>


<#macro LocalDateColumn property>
<@columnheader property=property/>
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
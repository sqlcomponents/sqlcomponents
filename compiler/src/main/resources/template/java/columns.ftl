<#macro columnheader property>
public static class ${property.name?cap_first}Column extends Column {
    private String sql;

    public ${property.name?cap_first}Column(final String columnName, final PartialWhereClause  whereClause) {
        super(columnName, whereClause);
    }
</#macro>

<#macro columnfooter>
    @Override
    protected String asSql() {
        return sql;
    }
}
</#macro>


<#macro StringColumn property>
<@columnheader property=property/>
    public final WhereClause  eq(final String value) {
        sql = columnName + "='" + value + "'";
        return getWhereClause();
    }

    public final WhereClause  like(final String value) {
        sql = columnName + " LIKE '" + value + "'";
        return getWhereClause();
    }
<@columnfooter/>
</#macro>

<#macro UUIDColumn property>
<@columnheader property=property/>
    public final WhereClause  eq(final String value) {
        sql = columnName + "='" + value + "'";
        return getWhereClause();
    }
<@columnfooter/>
</#macro>

<#macro DurationColumn property>
<@columnheader property=property/>
    public final WhereClause  eq(final String value) {
        sql = columnName + "='" + value + "'";
        return getWhereClause();
    }
<@columnfooter/>
</#macro>

<#macro JSONObjectColumn property>
<@columnheader property=property/>
    public final WhereClause  eq(final String value) {
        sql = columnName + "='" + value + "'";
        return getWhereClause();
    }
<@columnfooter/>
</#macro>

<#macro CharacterColumn property>
<@columnheader property=property/>
    public final WhereClause  eq(final String value) {
        sql = columnName + "='" + value + "'";
        return getWhereClause();
    }

    public final WhereClause  like(final String value) {
        sql = columnName + " LIKE '" + value + "'";
        return getWhereClause();
    }
<@columnfooter/>
</#macro>


<#macro BooleanColumn property>
<@columnheader property=property/>
    public final WhereClause  eq(final Boolean value) {
        sql = columnName + "=" + value ;
        return getWhereClause();
    }
<@columnfooter/>
</#macro>

<#macro numbercolumn type>
    public final WhereClause eq(final ${type} value) {
        sql = columnName + "=" + value;
        return getWhereClause();
    }

    public final WhereClause gt(final ${type} value) {
        sql = columnName + ">" + value;
        return getWhereClause();
    }

    public final WhereClause  gte(final ${type} value) {
        sql = columnName + ">=" + value;
        return getWhereClause();
    }

    public final WhereClause  lt(final ${type} value) {
        sql = columnName + "<" + value;
        return getWhereClause();
    }

    public final WhereClause  lte(final ${type} value) {
        sql = columnName + "<=" + value;
        return getWhereClause();
    }
</#macro>

<#macro LongColumn property>
<@columnheader property=property/>

    <@numbercolumn type="Long"/>

<@columnfooter/>
</#macro>

<#macro ShortColumn property>
<@columnheader property=property/>

    <@numbercolumn type="Short"/>

<@columnfooter/>
</#macro>


<#macro IntegerColumn property>
<@columnheader property=property/>

<@numbercolumn type="Integer"/>

<@columnfooter/>
</#macro>

<#macro ByteColumn property>
<@columnheader property=property/>

    <@numbercolumn type="Byte"/>

<@columnfooter/>
</#macro>
<#macro DoubleColumn property>
<@columnheader property=property/>

   <@numbercolumn type="Double"/>

<@columnfooter/>
</#macro>

<#macro FloatColumn property>
<@columnheader property=property/>
  <@numbercolumn type="Float"/>
<@columnfooter/>
</#macro>


<#macro LocalDateColumn property>
<@columnheader property=property/>
    public final WhereClause  eq(final LocalDate value) {
        sql = columnName + "=" + value;
        return getWhereClause();
    }

    public final WhereClause  gt(final LocalDate value) {
        sql = columnName + ">" + value;
        return getWhereClause();
    }

    public final WhereClause  gte(final LocalDate value) {
        sql = columnName + ">=" + value;
        return getWhereClause();
    }

    public final WhereClause  lt(final LocalDate value) {
        sql = columnName + "<" + value;
        return getWhereClause();
    }

    public final WhereClause  lte(final LocalDate value) {
        sql = columnName + "<=" + value;
        return getWhereClause();
    }

<@columnfooter/>
</#macro>

<#macro LocalTimeColumn property>
<@columnheader property=property/>


    public final WhereClause  eq(final LocalTime value) {
        sql = columnName + "=" + value;
        return getWhereClause();
    }

    public final WhereClause  gt(final LocalTime value) {
        sql = columnName + ">" + value;
        return getWhereClause();
    }

    public final WhereClause  gte(final LocalTime value) {
        sql = columnName + ">=" + value;
        return getWhereClause();
    }

    public final WhereClause  lt(final LocalTime value) {
        sql = columnName + "<" + value;
        return getWhereClause();
    }

    public final WhereClause  lte(final LocalTime value) {
        sql = columnName + "<=" + value;
        return getWhereClause();
    }


<@columnfooter/>
</#macro>

<#macro LocalDateTimeColumn property>
<@columnheader property=property/>

    public final WhereClause  eq(final LocalDateTime value) {
        sql = columnName + "=" + value;
        return getWhereClause();
    }

    public final WhereClause  gt(final LocalDateTime value) {
        sql = columnName + ">" + value;
        return getWhereClause();
    }

    public final WhereClause  gte(final LocalDateTime value) {
        sql = columnName + ">=" + value;
        return getWhereClause();
    }

    public final WhereClause  lt(final LocalDateTime value) {
        sql = columnName + "<" + value;
        return getWhereClause();
    }

    public final WhereClause  lte(final LocalDateTime value) {
        sql = columnName + "<=" + value;
        return getWhereClause();
    }

<@columnfooter/>
</#macro>
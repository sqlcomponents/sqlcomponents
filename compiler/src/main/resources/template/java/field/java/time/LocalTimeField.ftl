<#include "/template/java/field/field-header.ftl">

public class <#if column.nullable == "YES">Nullable</#if>LocalTimeField extends Field {
    protected String sql;

    public <#if column.nullable == "YES">Nullable</#if>LocalTimeField(final String columnName, final PartialWhereClause whereClause) {
        super(columnName, whereClause);
    }

    public WhereClause  eq(final LocalTime value) {
        sql = columnName + "=" + value;
        return getWhereClause ();
    }

    public WhereClause  gt(final LocalTime value) {
        sql = columnName + ">" + value;
        return getWhereClause ();
    }

    public WhereClause  gte(final LocalTime value) {
        sql = columnName + ">=" + value;
        return getWhereClause ();
    }

    public WhereClause  lt(final LocalTime value) {
        sql = columnName + "<" + value;
        return getWhereClause ();
    }

    public WhereClause  lte(final LocalTime value) {
        sql = columnName + "<=" + value;
        return getWhereClause ();
    }

    @Override
    protected String asSql() {
        return sql;
    }
}

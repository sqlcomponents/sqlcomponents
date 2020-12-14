<#include "field-header.ftl">

public class <#if column.nullable == "YES">Nullable</#if>LocalDateTimeField extends Field {
    protected String sql;

    public <#if column.nullable == "YES">Nullable</#if>LocalDateTimeField(final String columnName, final PartialWhereClause whereClause) {
        super(columnName, whereClause);
    }

    public WhereClause  eq(final LocalDateTime value) {
        sql = columnName + "=" + value;
        return getWhereClause ();
    }

    public WhereClause  gt(final LocalDateTime value) {
        sql = columnName + ">" + value;
        return getWhereClause ();
    }

    public WhereClause  gte(final LocalDateTime value) {
        sql = columnName + ">=" + value;
        return getWhereClause ();
    }

    public WhereClause  lt(final LocalDateTime value) {
        sql = columnName + "<" + value;
        return getWhereClause ();
    }

    public WhereClause  lte(final LocalDateTime value) {
        sql = columnName + "<=" + value;
        return getWhereClause ();
    }

    @Override
    protected String asSql() {
        return sql;
    }
}

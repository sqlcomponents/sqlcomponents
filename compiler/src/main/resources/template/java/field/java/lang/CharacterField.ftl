<#include "field-header.ftl">

public class <#if column.nullable == "YES">Nullable</#if>CharacterField extends Field {
    protected String sql;

    public <#if column.nullable == "YES">Nullable</#if>CharacterField(final String columnName, final PartialWhereClause whereClause) {
        super(columnName, whereClause);
    }

    public WhereClause eq(final Character value) {
        sql = columnName + "='" + value + "'";
        return getWhereClause ();
    }

    public WhereClause like(final Character value) {
        sql = columnName + " LIKE '" + value + "'";
        return getWhereClause ();
    }

    @Override
    public String asSql() {
        return sql;
    }
}

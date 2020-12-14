<#include "/template/java/field/field-header.ftl">

public class <#if column.nullable == "YES">Nullable</#if>BooleanField extends Field {
    protected String sql;

    public <#if column.nullable == "YES">Nullable</#if>BooleanField(final String columnName, final PartialWhereClause whereClause) {
        super(columnName, whereClause);
    }

    public WhereClause eq(final Boolean value) {
        sql = columnName + "='" + value + "'";
        return getWhereClause ();
    }

    @Override
    public String asSql() {
        return sql;
    }
}

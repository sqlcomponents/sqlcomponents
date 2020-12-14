<#include "/template/java/field/field-header.ftl">
import java.time.LocalDate;
public class <#if column.nullable == "YES">Nullable</#if>LocalDateField extends Field {
    protected String sql;

    public <#if column.nullable == "YES">Nullable</#if>LocalDateField(final String columnName, final PartialWhereClause whereClause) {
        super(columnName, whereClause);
    }

    public WhereClause  eq(final LocalDate value) {
        sql = columnName + "=" + value;
        return getWhereClause ();
    }

    public WhereClause  gt(final LocalDate value) {
        sql = columnName + ">" + value;
        return getWhereClause ();
    }

    public WhereClause  gte(final LocalDate value) {
        sql = columnName + ">=" + value;
        return getWhereClause ();
    }

    public WhereClause  lt(final LocalDate value) {
        sql = columnName + "<" + value;
        return getWhereClause ();
    }

    public WhereClause  lte(final LocalDate value) {
        sql = columnName + "<=" + value;
        return getWhereClause ();
    }

    @Override
    protected String asSql() {
        return sql;
    }
}

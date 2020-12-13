package ${entity.orm.application.rootPackage}.common.field;

import ${entity.orm.application.rootPackage}.common.Field;
import ${entity.orm.application.rootPackage}.common.PartialWhereClause;
import ${entity.orm.application.rootPackage}.common.WhereClause;

public class <#if column.nullable == "YES">Nullable</#if>NumberField<T extends Number> extends Field {

    protected String sql;

    public <#if column.nullable == "YES">Nullable</#if>NumberField(final String columnName, final PartialWhereClause  whereClause) {
        super(columnName, whereClause);
    }

    public WhereClause eq(final T value) {
        sql = columnName + "=" + value;
        return getWhereClause();
    }

    public WhereClause  gt(final T value) {
        sql = columnName + ">" + value;
        return getWhereClause();
    }

    public WhereClause  gte(final T value) {
        sql = columnName + ">=" + value;
        return getWhereClause ();
    }

    public WhereClause  lt(final T value) {
        sql = columnName + "<" + value;
        return getWhereClause ();
    }

    public WhereClause  lte(final T value) {
        sql = columnName + "<=" + value;
        return getWhereClause ();
    }

    @Override
    protected String asSql() {
        return sql;
    }
}

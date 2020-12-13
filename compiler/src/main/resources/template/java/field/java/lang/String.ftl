package ${entity.orm.application.rootPackage}.common.field;

import ${entity.orm.application.rootPackage}.common.Field;
import ${entity.orm.application.rootPackage}.common.PartialWhereClause;
import ${entity.orm.application.rootPackage}.common.WhereClause;

public class <#if column.nullable == "YES">Nullable</#if>StringField extends Field {
    protected String sql;

    public <#if column.nullable == "YES">Nullable</#if>StringField(final String columnName, final PartialWhereClause whereClause) {
        super(columnName, whereClause);
    }

    public WhereClause eq(final String value) {
        sql = columnName + "='" + value + "'";
        return getWhereClause ();
    }

    public WhereClause like(final String value) {
        sql = columnName + " LIKE '" + value + "'";
        return getWhereClause ();
    }

    @Override
    public String asSql() {
        return sql;
    }
}

package ${rootPackage}.common;

public abstract class Field {

    protected final String columnName;
    private final PartialWhereClause whereClause ;

    public Field(final String columnName, final PartialWhereClause  whereClause) {
        this.columnName = columnName;
        this.whereClause  = whereClause ;
    }

    protected WhereClause  getWhereClause () {
        return (WhereClause) whereClause ;
    }

    protected abstract String asSql();

}
<#if table.tableType == 'TABLE' >

public DeleteStatement delete(WhereClause whereClause) {
    return new DeleteStatement(whereClause);
}

public DeleteStatement delete() {
    return new DeleteStatement(null);
}

public final class DeleteStatement implements DataManager.Sql<Integer> {

    private final WhereClause whereClause;

    private DeleteStatement() {
            this(null);
        }

        private DeleteStatement(final WhereClause whereClause) {
            this.whereClause = whereClause;
        }

    @Override
    public Integer execute(final Connection connection) throws SQLException  {
    	final String query = "DELETE FROM ${table.escapedName?j_string}" 
        + ( this.whereClause == null ? "" : (" WHERE " + this.whereClause.asSql()) );
        return dataManager.sql(query).execute(connection);
	}

    public DataManager.DeleteQuery<Value<?,?>> sql(final String sql) {
        return new DataManager.DeleteQuery<>(sql);
    }



}

    <#assign a=addImportStatement(beanPackage+"."+name)>
    <#assign a=addImportStatement("java.sql.Statement")>
</#if>
<#if table.tableType == 'TABLE' >

public DeleteStatement delete() {
    return new DeleteStatement();
}

public final class DeleteStatement implements DataManager.Sql<Integer> {

    @Override
    public Integer execute(final Connection connection) throws SQLException  {
        return dataManager.sql("DELETE FROM ${table.escapedName?j_string}")
                .execute(connection);
	}

    public DataManager.Sql<Integer> where(final WhereClause whereClause) {
        final String query = "DELETE FROM ${table.escapedName?j_string}"
                                + ( whereClause == null ? "" : (" WHERE " + whereClause.asSql()) );
        return dataManager.sql(query);
    }

    public DataManager.Statement<Value<?,?>> sql(final String sql) {
        return new DataManager.Statement<>(sql);
    }

}

    <#assign a=addImportStatement(beanPackage+"."+name)>
    <#assign a=addImportStatement("java.sql.Statement")>
</#if>
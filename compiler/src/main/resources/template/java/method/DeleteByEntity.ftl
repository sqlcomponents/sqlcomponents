<#if table.tableType == 'TABLE' >

public DeleteStatement delete(WhereClause whereClause) {
    return new DeleteStatement(this,whereClause);
}

public static final class DeleteStatement {

    private final ${name}Store${orm.daoSuffix} ${name?uncap_first}Store${orm.daoSuffix};
    private final WhereClause whereClause;

    private DeleteStatement(final ${name}Store${orm.daoSuffix} ${name?uncap_first}Store${orm.daoSuffix}) {
            this(${name?uncap_first}Store${orm.daoSuffix},null);
        }

        private DeleteStatement(final ${name}Store${orm.daoSuffix} ${name?uncap_first}Store${orm.daoSuffix}
                ,final WhereClause whereClause) {
            this.${name?uncap_first}Store${orm.daoSuffix} = ${name?uncap_first}Store${orm.daoSuffix};
            this.whereClause = whereClause;
        }

    public int execute() throws SQLException  {
        String whereSQL = whereClause.asSql() ;
    	final String query = "DELETE FROM ${table.escapedName?j_string}" + (whereSQL == null ? "" : (" WHERE " + whereSQL));
        try (java.sql.Connection dbConnection = this.${name?uncap_first}Store${orm.daoSuffix}.dbDataSource.getConnection();
			Statement statement = dbConnection.createStatement()) {
            return statement.executeUpdate(query);
        }
	}
}


    <#assign a=addImportStatement(beanPackage+"."+name)>
    <#assign a=addImportStatement("java.sql.Statement")>
</#if>
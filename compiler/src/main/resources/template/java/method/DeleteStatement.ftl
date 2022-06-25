<#if table.tableType == 'TABLE' >

<#if table.hasPrimaryKey>
public int delete(${getPrimaryKeysAsParameterString()}) throws SQLException  {
		final String query = <@compress single_line=true>"DELETE FROM ${table.escapedName?j_string}
					WHERE
					<#assign index=0>
					<#list properties as property>
						<#if property.column.primaryKeyIndex != 0>
						<#if index == 0><#assign index=1><#else>,</#if>${property.column.escapedName?j_string} = ?
						</#if>
					</#list></@compress>";
        try (java.sql.Connection dbConnection = dbDataSource.getConnection();
			PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
			${getPrimaryKeysAsPreparedStatements()}
            return preparedStatement.executeUpdate();
        }
	}
    </#if>
    <#assign a=addImportStatement("java.sql.PreparedStatement")>

public DeleteStatement delete(WhereClause whereClause) {
    return new DeleteStatement(this,whereClause);
}

public DeleteStatement delete() {
    return new DeleteStatement(this,null);
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
    	final String query = "DELETE FROM ${table.escapedName?j_string}" 
        + ( this.whereClause == null ? "" : (" WHERE " + this.whereClause.asSql()) );
        try (java.sql.Connection dbConnection = this.${name?uncap_first}Store${orm.daoSuffix}.dbDataSource.getConnection();
			Statement statement = dbConnection.createStatement()) {
            return statement.executeUpdate(query);
        }
	}
}


    <#assign a=addImportStatement(beanPackage+"."+name)>
    <#assign a=addImportStatement("java.sql.Statement")>
</#if>
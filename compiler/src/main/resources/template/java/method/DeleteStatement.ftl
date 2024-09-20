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

    private final ${name}Store ${name?uncap_first}Store;
    private final WhereClause whereClause;

    private DeleteStatement(final ${name}Store ${name?uncap_first}Store) {
            this(${name?uncap_first}Store,null);
        }

        private DeleteStatement(final ${name}Store ${name?uncap_first}Store
                ,final WhereClause whereClause) {
            this.${name?uncap_first}Store = ${name?uncap_first}Store;
            this.whereClause = whereClause;
        }

    public int execute() throws SQLException  {
        int deletedRows = 0;
    	final String query = "DELETE FROM ${table.escapedName?j_string}" 
        + ( this.whereClause == null ? "" : (" WHERE " + this.whereClause.asSql()) );
        try (java.sql.Connection dbConnection = this.${name?uncap_first}Store.dbDataSource.getConnection();
			Statement statement = dbConnection.createStatement()) {
            deletedRows = statement.executeUpdate(query);
        }
        return deletedRows;
	}

    public DeleteQuery sql(final String sql) {
            return new DeleteQuery(this, sql);
    }

    public static final class DeleteQuery  {

        private final DeleteStatement deleteStatement;
        private final String sql;
        private final List<Value> values;

        public DeleteQuery(final DeleteStatement deleteStatement, final String sql) {
            this.deleteStatement = deleteStatement;
            this.sql = sql;
            this.values = new ArrayList<>();
        }


        public DeleteQuery param(final Value value) {
            this.values.add(value);
            return this;
        }

        public int execute() throws SQLException {
            int deletedRows = 0 ;
            try (java.sql.Connection dbConnection = this.deleteStatement.${name?uncap_first}Store.dbDataSource.getConnection();
                PreparedStatement preparedStatement = dbConnection.prepareStatement(sql)) {

                    int index = 1;
                for (Value value:values
                     ) {
                    value.set(preparedStatement, index++);
                }

                deletedRows = preparedStatement.executeUpdate();
            }
            return deletedRows;
        }


    }

}


    <#assign a=addImportStatement(beanPackage+"."+name)>
    <#assign a=addImportStatement("java.sql.Statement")>
</#if>
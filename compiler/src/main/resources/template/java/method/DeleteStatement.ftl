<#if table.tableType == 'TABLE' >

<#if table.hasPrimaryKey>
public int delete(final DataSource dataSource,${getPrimaryKeysAsParameterString()}) throws SQLException  {
		final String query = <@compress single_line=true>"DELETE FROM ${table.escapedName?j_string}
					WHERE
					<#assign index=0>
					<#list properties as property>
						<#if property.column.primaryKeyIndex != 0>
						<#if index == 0><#assign index=1><#else>,</#if>${property.column.escapedName?j_string} = ?
						</#if>
					</#list></@compress>";
        DataManager.SqlBuilder sqlBuilder = dataManager.sql(query);
        ${getPrimaryKeysAsPreparedStatements()}
        return sqlBuilder.execute(dataSource);
	}
    </#if>
    <#assign a=addImportStatement("java.sql.PreparedStatement")>

public DeleteStatement delete(WhereClause whereClause) {
    return new DeleteStatement(whereClause);
}

public DeleteStatement delete() {
    return new DeleteStatement(null);
}

public final class DeleteStatement {

    private final WhereClause whereClause;

    private DeleteStatement() {
            this(null);
        }

        private DeleteStatement(final WhereClause whereClause) {
            this.whereClause = whereClause;
        }

    public int execute(final DataSource dataSource) throws SQLException  {
    	final String query = "DELETE FROM ${table.escapedName?j_string}" 
        + ( this.whereClause == null ? "" : (" WHERE " + this.whereClause.asSql()) );
        return dataManager.sql(query).execute(dataSource);
	}

    public DeleteQuery sql(final String sql) {
            return new DeleteQuery(sql);
    }

    public final class DeleteQuery  {

        private final String sql;
        private final List<Value<?,?>> values;

        public DeleteQuery(final String sql) {
            this.sql = sql;
            this.values = new ArrayList<>();
        }


        public DeleteQuery param(final Value<?,?> value) {
            this.values.add(value);
            return this;
        }

        public int execute(final DataSource dataSource) throws SQLException {
            DataManager.SqlBuilder sqlBuilder = dataManager.sql(sql);

            for (Value<?,?> value:values) {
                value.set(sqlBuilder);
            }
            
            return sqlBuilder.execute(dataSource);
        }


    }

}


    <#assign a=addImportStatement(beanPackage+"."+name)>
    <#assign a=addImportStatement("java.sql.Statement")>
</#if>
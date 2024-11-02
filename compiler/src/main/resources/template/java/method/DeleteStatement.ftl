<#if table.tableType == 'TABLE' >

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
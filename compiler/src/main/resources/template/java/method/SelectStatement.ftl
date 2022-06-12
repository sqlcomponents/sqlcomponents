<#assign a=addImportStatement(beanPackage+"."+name)>
<#assign a=addImportStatement("java.util.List")>
<#assign a=addImportStatement("java.util.ArrayList")>

public SelectStatement select() {
        return new SelectStatement(this);
}
public SelectStatement select(WhereClause whereClause) throws SQLException  {
        return new SelectStatement(this,whereClause);
	}

public static final class SelectStatement {

        private final ${name}Store${orm.daoSuffix} ${name?uncap_first}Store${orm.daoSuffix};
        private final WhereClause whereClause;

        private SelectStatement(final ${name}Store${orm.daoSuffix} ${name?uncap_first}Store${orm.daoSuffix}) {
            this(${name?uncap_first}Store${orm.daoSuffix},null);
        }

        private SelectStatement(final ${name}Store${orm.daoSuffix} ${name?uncap_first}Store${orm.daoSuffix}
                ,final WhereClause whereClause) {
            this.${name?uncap_first}Store${orm.daoSuffix} = ${name?uncap_first}Store${orm.daoSuffix};
            this.whereClause = whereClause;
        }

        public List<${name}> execute() throws SQLException {
		final String query = this.whereClause == null ? <@compress single_line=true>"
                SELECT
		<@columnSelection/> 
		FROM ${table.escapedName?j_string}
                </@compress>" : "SELECT <@columnSelection/> FROM ${table.escapedName?j_string}"+ (" WHERE " + this.whereClause.asSql());
                try (java.sql.Connection dbConnection = this.${name?uncap_first}Store${orm.daoSuffix}.dbDataSource.getConnection();
                PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
                
                ResultSet resultSet = preparedStatement.executeQuery();
                                List<${name}> arrays = new ArrayList();
                while (resultSet.next()) {
                                        arrays.add(this.${name?uncap_first}Store${orm.daoSuffix}.rowMapper(resultSet));
                                }
                                return arrays;
                } 
	}
}



    
<#assign a=addImportStatement("java.util.List")>

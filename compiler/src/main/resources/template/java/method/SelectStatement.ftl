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

        private LimitClause limitClause;
        private LimitClause.OffsetClause offsetClause;

        public LimitClause limit(final int limit) {
                return new LimitClause(this,limit);
        }

        private SelectStatement(final ${name}Store${orm.daoSuffix} ${name?uncap_first}Store${orm.daoSuffix}) {
            this(${name?uncap_first}Store${orm.daoSuffix},null);
        }

        private SelectStatement(final ${name}Store${orm.daoSuffix} ${name?uncap_first}Store${orm.daoSuffix}
                ,final WhereClause whereClause) {
            this.${name?uncap_first}Store${orm.daoSuffix} = ${name?uncap_first}Store${orm.daoSuffix};
            this.whereClause = whereClause;
        }

        public List<${name}> execute() throws SQLException {
		final String query = <@compress single_line=true>"
                SELECT
		<@columnSelection/> 
		FROM ${table.escapedName?j_string}
                </@compress>" 
                + ( this.whereClause == null ? "" : (" WHERE " + this.whereClause.asSql()) )
                + ( this.limitClause == null ? "" : this.limitClause.asSql() )
                + ( this.offsetClause == null ? "" : this.offsetClause.asSql() );
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

        public static final class LimitClause  {
                private final SelectStatement selectStatement;
                private final String asSql;

                private LimitClause(final SelectStatement selectStatement,final int limit) {
                        this.selectStatement = selectStatement;
                        asSql = " LIMIT " + limit;

                        this.selectStatement.limitClause = this;
                }

                private String asSql() {
                        return asSql ;
                }

                public List<${name}> execute() throws SQLException {
                        return this.selectStatement.execute();
                }

                public OffsetClause offset(final int offset) {
                        return new OffsetClause(this.selectStatement,offset);
                }

                public static final class OffsetClause  {
                        private final SelectStatement selectStatement;
                        private final String asSql;

                        private OffsetClause(final SelectStatement selectStatement,final int offset) {
                                this.selectStatement = selectStatement;
                                asSql = " OFFSET " + offset;

                                this.selectStatement.offsetClause = this;
                        }

                        private String asSql() {
                                return asSql ;
                        }

                        public List<${name}> execute() throws SQLException {
                                return this.selectStatement.execute();
                        }

        }

        }
}



    
<#assign a=addImportStatement("java.util.List")>

<#assign a=addImportStatement(beanPackage+"."+name)>
<#assign a=addImportStatement("java.util.List")>
<#assign a=addImportStatement("java.util.ArrayList")>
<#assign a=addImportStatement("java.util.Optional")>
<#assign a=addImportStatement("java.sql.PreparedStatement")>

public SelectStatementWithWhere select() {
        return new SelectStatementWithWhere();
}

public final class SelectStatementWithWhere extends SelectStatement{

        private SelectStatementWithWhere() {
            super(null);
        }

        public SelectStatement where(WhereClause whereClause) throws SQLException {
            return new SelectStatement(whereClause);
        }
    }

public sealed class SelectStatement permits SelectStatementWithWhere {


        private final WhereClause whereClause;

        private LimitClause limitClause;
        private LimitClause.OffsetClause offsetClause;


        public LimitClause limit(final int limit) {
                return new LimitClause(limit);
        }

        private SelectStatement() {
            this(null);
        }

        private SelectStatement(final WhereClause whereClause) {
            this.whereClause = whereClause;
        }

        public final List<${name}> execute(final DataSource dataSource) throws <@throwsblock/> {
            
		final String query = <@compress single_line=true>"SELECT
		<@columnSelection properties=properties/> 
		FROM ${table.escapedName?j_string}
                </@compress>" 
                + ( this.whereClause == null ? "" : (" WHERE " + this.whereClause.asSql()) )
                + ( this.limitClause == null ? "" : this.limitClause.asSql() )
                + ( this.offsetClause == null ? "" : this.offsetClause.asSql() );
                return dataManager.sql(query).queryForList(${name}Store.this::rowMapper).execute(dataSource);
	}

        public final int count(final DataSource dataSource) throws SQLException {
		final String query = <@compress single_line=true>"SELECT
		COUNT(<#if primaryKeyProperties?size == 0
                >*<#else
                    ><@columnSelection properties=primaryKeyProperties
                /></#if>)
		FROM ${table.escapedName?j_string}
                </@compress>" 
                + ( this.whereClause == null ? "" : (" WHERE " + this.whereClause.asSql()) );
                return dataManager.sql(query).queryForInt().execute(dataSource);
	}
public final SelectQuery sql(final String sql) {
            return new SelectQuery(sql);
    }

    public final class SelectQuery  {

        private final String sql;
        private final List<Value<?,?>> values;

        public SelectQuery(final String sql) {
            this.sql = sql;
            this.values = new ArrayList<>();
        }


        public SelectQuery param(final Value<?,?> value) {
            this.values.add(value);
            return this;
        }

        public Optional<${name}> optional(final DataSource dataSource) throws <@throwsblock/> {
            DataManager.SqlBuilder sqlBuilder = dataManager.sql(sql);

            for (Value<?,?> value:values) {
                value.set(sqlBuilder);
            }
            
            return Optional.ofNullable(sqlBuilder.queryForOne(${name}Store.this::rowMapper).execute(dataSource));
        }

        public List<${name}> list(final DataSource dataSource) throws <@throwsblock/>{
            DataManager.SqlBuilder sqlBuilder = dataManager.sql(sql);

            for (Value<?,?> value:values) {
                value.set(sqlBuilder);
            }
            
            return sqlBuilder.queryForList(${name}Store.this::rowMapper).execute(dataSource);
        }
    }


        public final class LimitClause  {

                private final String asSql;

                private LimitClause(final int limit) {
                        asSql = " LIMIT " + limit;

                        limitClause = this;
                }

                private String asSql() {
                        return asSql ;
                }

                public OffsetClause offset(final int offset) {
                        return new OffsetClause(this,offset);
                }

                <#if hasJavaClass("org.springframework.data.domain.Page") >
                <#assign a=addImportStatement("org.springframework.data.domain.Page")>
                <#assign a=addImportStatement("org.springframework.data.domain.PageImpl")>
                <#assign a=addImportStatement("org.springframework.data.domain.Pageable")>
                public Page<${name}> execute(final Pageable pageable) throws <@throwsblock/> {
                    return new PageImpl(this.selectStatement.execute(dataSource), pageable,
                                selectStatement.count());
                }
                <#else>
                public DataManager.Page<${name}> execute(final DataSource dataSource) throws <@throwsblock/> {
                    return DataManager.page(SelectStatement.this.execute(dataSource), count(dataSource));
                }
                </#if>

                public final class OffsetClause  {
                        private final LimitClause limitClause;
                        private final String asSql;

                        private OffsetClause(final LimitClause limitClause,final int offset) {
                                this.limitClause = limitClause;
                                asSql = " OFFSET " + offset;

                                offsetClause = this;
                        }

                        private String asSql() {
                                return asSql ;
                        }

                        <#if hasJavaClass("org.springframework.data.domain.Page") >
                        public Page<${name}> execute(final Pageable pageable) throws <@throwsblock/> {
                                return this.limitClause.execute(pageable);
                        }
                        <#else>
                        public DataManager.Page<${name}> execute(final DataSource dataSource) throws <@throwsblock/> {
                                return this.limitClause.execute(dataSource);
                        }
                        </#if>


        }

        }
}



    






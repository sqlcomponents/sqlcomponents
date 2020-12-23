<#macro insertquery>
  final String query = <@compress single_line=true>"INSERT INTO ${table.escapedName} (
  		<#assign index=0>
  		<#list insertableProperties as property>
  			<#if index == 0><#assign index=1><#else>,</#if>${property.column.escapedName}
  		</#list>
  		)
  	    VALUES (
  	    <#assign index=0>
  	    <#list insertableProperties as property>
  			<#if index == 0><#if sequenceName?? && table.highestPKIndex == 1>
  			<#list properties as property>
  			    <#if property.column.primaryKeyIndex == 1>nextval('${sequenceName}')</#if>
  			 </#list>
  			 <#else>    ?</#if><#assign index=1><#else>            ,?</#if>
  		</#list>
  	    )
  		"</@compress>;
</#macro>

<#if table.tableType == 'TABLE' >


	public final InsertStatement insert() {
        return new InsertStatement(this
        <#list sampleDistinctCustomColumnTypeProperties as property>
                                                      ,this.convert${property.column.typeName?cap_first}
                                               </#list>);
    }

    <#if mustInsertableProperties?size != 0>
    public final InsertStatement insert(<#assign index=0>
            <#list mustInsertableProperties as property>
            <#if index != 0>,</#if>
            final Column.${property.name?cap_first}Column ${property.name}Column 
            <#assign index=1>
            </#list>) {
        return new InsertStatement(this
        <#list sampleDistinctCustomColumnTypeProperties as property>
               ,this.convert${property.column.typeName?cap_first}
        </#list>);
    }
    </#if>
    public static final class InsertStatement {
        private final ${name}Store${orm.daoSuffix} ${name?uncap_first}Store${orm.daoSuffix};

        <#list sampleDistinctCustomColumnTypeProperties as property>
        private final ${orm.application.name}Manager.ConvertFunction<${getClassName(property.dataType)},Object> convert${property.column.typeName?cap_first};
        </#list>

        private InsertStatement(final ${name}Store${orm.daoSuffix} ${name?uncap_first}Store${orm.daoSuffix}
        <#list sampleDistinctCustomColumnTypeProperties as property>
         ,final ${orm.application.name}Manager.ConvertFunction<${getClassName(property.dataType)},Object> theConvert${property.column.typeName?cap_first}
        </#list>
                ) {
            this.${name?uncap_first}Store${orm.daoSuffix} = ${name?uncap_first}Store${orm.daoSuffix};
            <#list sampleDistinctCustomColumnTypeProperties as property>
            this.convert${property.column.typeName?cap_first} =  theConvert${property.column.typeName?cap_first};
            </#list>
        }

        private void prepare(final PreparedStatement preparedStatement,final ${name} ${name?uncap_first}) throws SQLException {
            <#assign index=0>
            <#assign column_index=1>
            <#list insertableProperties as property>
            <#if index == 0>
            <#if sequenceName?? && property.column.primaryKeyIndex == 1>
            <#else>
            preparedStatement.set${getJDBCClassName(property.dataType)}(${column_index},${wrapSet(name?uncap_first,property)});
            <#assign column_index = column_index + 1>
            </#if>
            <#assign index=1>
            <#else>
            preparedStatement.set${getJDBCClassName(property.dataType)}(${column_index},${wrapSet(name?uncap_first,property)});
            <#assign column_index = column_index + 1>
            </#if>
            </#list>
        }

        public final ValueClause values(final ${name} ${name?uncap_first}) {
            return new ValueClause(${name?uncap_first},this);
        }

        public final ValuesClause values(final List<${name}> listOf${name}) {
            return new ValuesClause(listOf${name},this);
        }

        public static final class ValueClause  {
            
            private final InsertStatement insertStatement;

            private ${name} ${name?uncap_first};

            ValueClause (final ${name} ${name?uncap_first},final InsertStatement insertStatement) {
                this.${name?uncap_first} = ${name?uncap_first};
                this.insertStatement = insertStatement;
            }

            public final ValuesClause values(final ${name} the${name}) {
                List<${name}> listOf${name} = new ArrayList<>();
                listOf${name}.add(${name?uncap_first});
                listOf${name}.add(the${name});
                return new ValuesClause(listOf${name},insertStatement);
            }
            

            public final int execute() throws SQLException  {
                int insertedRows = 0;
                <@insertquery/>

                try (Connection conn = insertStatement.${name?uncap_first}Store${orm.daoSuffix}.dataSource.getConnection();
                     PreparedStatement preparedStatement = conn.prepareStatement(query))
                {
                    this.insertStatement.prepare(preparedStatement,${name?uncap_first});
                    insertedRows = preparedStatement.executeUpdate();
                }
                return insertedRows;
            }

            public final ${name} returning() throws SQLException  {
                ${name} inserted${name} = null ;
                <@insertquery/>

                try (Connection conn = insertStatement.${name?uncap_first}Store${orm.daoSuffix}.dataSource.getConnection();
                     PreparedStatement preparedStatement = conn.prepareStatement(query<#if table.hasAutoGeneratedPrimaryKey == true>, Statement.RETURN_GENERATED_KEYS</#if>))
                {
                    this.insertStatement.prepare(preparedStatement,${name?uncap_first});
                    if( preparedStatement.executeUpdate() == 1 ) {
                      <#if table.hasAutoGeneratedPrimaryKey == true>
                      ResultSet res = preparedStatement.getGeneratedKeys();
                      if (res.next()) {
                          inserted${name} =  insertStatement.${name?uncap_first}Store${orm.daoSuffix}.find(${getGeneratedPrimaryKeysFromRS()});
                      }
                      <#else>
                      inserted${name} =  insertStatement.${name?uncap_first}Store${orm.daoSuffix}.find(${getPrimaryKeysFromModel(name?uncap_first)});
                      </#if>
                  }
                }
                return inserted${name};
            }
        }

        public static final class ValuesClause  {

            
            private final InsertStatement insertStatement;

            private List<${name}> listOf${name};

            ValuesClause(final List<${name}> theListOf${name},final InsertStatement insertStatement) {
                this.listOf${name} = theListOf${name};
                this.insertStatement = insertStatement;
            }

            public final ValuesClause values(final ${name} the${name}) {
                this.listOf${name}.add(the${name});
                return this;
            }

            public final int[] execute() throws SQLException  {
                int[] insertedRows = null;
                <@insertquery/>
                
                try (Connection conn = insertStatement.${name?uncap_first}Store${orm.daoSuffix}.dataSource.getConnection();
                     PreparedStatement preparedStatement = conn.prepareStatement(query))
                {
                    for (${name} ${name?uncap_first}:listOf${name}) {
                        this.insertStatement.prepare(preparedStatement, ${name?uncap_first});
                        preparedStatement.addBatch();
                    }
                    insertedRows = preparedStatement.executeBatch();
                }
                return insertedRows;
            }

            public final List<${name}> returning() throws SQLException  {
                List<${name}> insertedList = null ;
                <@insertquery/>

                try (Connection conn = insertStatement.${name?uncap_first}Store${orm.daoSuffix}.dataSource.getConnection();
                     PreparedStatement preparedStatement = conn.prepareStatement(query<#if table.hasAutoGeneratedPrimaryKey == true>, Statement.RETURN_GENERATED_KEYS</#if>))
                {
                    for (${name} ${name?uncap_first}:listOf${name}) {
                        this.insertStatement.prepare(preparedStatement, ${name?uncap_first});
                        preparedStatement.addBatch();
                    }

                    int[] insertedCounts = preparedStatement.executeBatch();

                    if ( insertedCounts != null ) {
                        insertedList = new ArrayList<>();

                        <#if table.hasAutoGeneratedPrimaryKey == true>
                             ResultSet res = preparedStatement.getGeneratedKeys();
                        </#if>

                        for (int i = 0; i < insertedCounts.length; i++) {

                          int insertedRows = insertedCounts[i];
                          <#if table.hasAutoGeneratedPrimaryKey == true>
                          if (insertedRows == 1 && res.next()) {
                            insertedList.add(insertStatement.${name?uncap_first}Store${orm.daoSuffix}.find(${getGeneratedPrimaryKeysFromRS()})) ;
                          }
                          <#else>
                          if (insertedRows == 1) {
                            insertedList.add(insertStatement.${name?uncap_first}Store${orm.daoSuffix}.find(${getPrimaryKeysFromModel("listOf${name}.get(i)")}));
                          }
                          </#if>
                          else {
                            insertedList.add(null);
                          }
                        }

                    }




                }
                return insertedList;
            }


        }
    }
	<#assign a=addImportStatement(beanPackage+"."+name)><#assign a=addImportStatement("java.sql.PreparedStatement")><#assign a=addImportStatement("java.sql.Statement")>
</#if>
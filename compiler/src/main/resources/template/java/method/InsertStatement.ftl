<#macro insertquery isReturning=false>
  final String query = <@compress single_line=true>"INSERT INTO ${table.escapedName?j_string} (
  		<#assign index=0>
  		<#list insertableProperties as property>
  			<#if index == 0><#assign index=1><#else>,</#if>${property.column.escapedName?j_string}
  		</#list>
  		)
  	    VALUES (
  	    <#assign index=0>
  	    <#list insertableProperties as property>
  			<#if index == 0><#if sequenceName?? && table.highestPKIndex == 1>
  			<#list properties as property>
  			    <#if property.column.primaryKeyIndex == 1>nextval('${sequenceName}')</#if>
  			 </#list>
  			 <#else>    ${getPreparedValue(property,orm.insertMap)}</#if><#assign index=1><#else>            ,${getPreparedValue(property,orm.insertMap)}</#if>
  		</#list>
  	    )
  	    <#if isReturning && (returningProperties?size > 0)>
          <#if orm.database.dbType == 'POSTGRES' && orm.database.databaseMajorVersion gt 10>
            returning <@returningColumnSelection/>
          </#if>
  	    </#if>
  		"</@compress>;
</#macro>



<#if table.tableType == 'TABLE' >


	public InsertStatement insert() {
        return new InsertStatement(this
        <#if containsEncryptedProperty() >
            ,this.encryptionFunction
            ,this.decryptionFunction
        </#if>);
    }

    <#if mustInsertableProperties?size != 0>
    public InsertStatement insert(<#assign index=0>
            <#list mustInsertableProperties as property>
            <#if index != 0>,</#if>
            final Column.${property.name?cap_first}Column ${property.name}Column 
            <#assign index=1>
            </#list>) {
        return new InsertStatement(this
        
        <#if containsEncryptedProperty() >
            ,this.encryptionFunction
            ,this.decryptionFunction
        </#if>
        );
    }
    </#if>
    public static final class InsertStatement {
        private final ${name}Store ${name?uncap_first}Store;


        <#if containsEncryptedProperty() >
            private final Function<String,String> encryptionFunction;
            private final Function<String,String> decryptionFunction;
        </#if>

        private InsertStatement(final ${name}Store ${name?uncap_first}Store
        <#if containsEncryptedProperty() >
            ,final Function<String,String> encryptionFunction
            ,final Function<String,String> decryptionFunction
        </#if>
                ) {
            this.${name?uncap_first}Store = ${name?uncap_first}Store;

            <#if containsEncryptedProperty() >
            this.encryptionFunction = encryptionFunction;
            this.decryptionFunction = decryptionFunction;
            </#if>
        }

        private void prepare(final PreparedStatement preparedStatement,final ${name} ${name?uncap_first}) throws SQLException {
            <#assign index=0>
            <#assign column_index=1>
            <#list insertableProperties as property>
            <#if containsProperty(property,orm.insertMap)>
                <#if index == 0>
                <#if sequenceName?? && property.column.primaryKeyIndex == 1>
                <#else>
                ${property.name?uncap_first}(${name?uncap_first+".get"+property.name?cap_first + "()"}).set(preparedStatement,${column_index});
                <#assign column_index = column_index + 1>
                </#if>
                <#assign index=1>
                <#else>
                ${property.name?uncap_first}(${name?uncap_first+".get"+property.name?cap_first + "()"}).set(preparedStatement,${column_index});
                <#assign column_index = column_index + 1>
                </#if>
            </#if>
            </#list>
        }

        public ValueClause values(final ${name} ${name?uncap_first}) {
            return new ValueClause(${name?uncap_first},this);
        }

        public ValuesClause values(final List<${name}> listOf${name}) {
            return new ValuesClause(listOf${name},this);
        }

        public static final class ValueClause  {
            
            private final InsertStatement insertStatement;

            private ${name} ${name?uncap_first};

            ValueClause (final ${name} ${name?uncap_first},final InsertStatement insertStatement) {
                this.${name?uncap_first} = ${name?uncap_first};
                this.insertStatement = insertStatement;
            }

            public ValuesClause values(final ${name} the${name}) {
                List<${name}> listOf${name} = new ArrayList<>();
                listOf${name}.add(${name?uncap_first});
                listOf${name}.add(the${name});
                return new ValuesClause(listOf${name},insertStatement);
            }
            

            public int execute() throws SQLException  {
                int insertedRows = 0;
                <@insertquery/>

                try (java.sql.Connection dbConnection = insertStatement.${name?uncap_first}Store.dbDataSource.getConnection();
                     PreparedStatement preparedStatement = dbConnection.prepareStatement(query))
                {
                    this.insertStatement.prepare(preparedStatement,${name?uncap_first});
                    insertedRows = preparedStatement.executeUpdate();
                }
                return insertedRows;
            }

            <#if table.hasPrimaryKey>

            public ${name} returning() throws <@throwsblock/>  {
                ${name} inserted${name} = null ;
                <@insertquery isReturning=true/>

                try (java.sql.Connection dbConnection = insertStatement.${name?uncap_first}Store.dbDataSource.getConnection();
                     PreparedStatement preparedStatement = dbConnection.prepareStatement(query<#if table.hasAutoGeneratedPrimaryKey == true><#if orm.database.dbType == 'POSTGRES' && orm.database.databaseMajorVersion gt 10 ><#else>, Statement.RETURN_GENERATED_KEYS</#if></#if>))
                {
                    this.insertStatement.prepare(preparedStatement,${name?uncap_first});

                    <#if orm.database.dbType == 'POSTGRES' && orm.database.databaseMajorVersion gt 10 >
                      <#if ((returningProperties?size) > 0)>
                                ResultSet resultSet = preparedStatement.executeQuery();

                                if (resultSet.next()) inserted${name} = this.insertStatement.${name?uncap_first}Store.rowMapperForReturning(resultSet, ${name?uncap_first});
                      <#else>
                        if( preparedStatement.executeUpdate() == 1 ) {
                             inserted${name} = ${name?uncap_first};
                        }
                      </#if>
                    <#else>
                    if( preparedStatement.executeUpdate() == 1 ) {
                                          <#if table.hasAutoGeneratedPrimaryKey == true>
                                          ResultSet res = preparedStatement.getGeneratedKeys();
                                          if (res.next()) {
                                              inserted${name} =  insertStatement.${name?uncap_first}Store.select(${getGeneratedPrimaryKeysFromRS()}).get();
                                          }
                                          <#else>
                                          inserted${name} =  insertStatement.${name?uncap_first}Store.select(${getPrimaryKeysFromModel(name?uncap_first)}).get();
                                          </#if>
                                      }
                    </#if>


                }
                return inserted${name};
            }
             </#if>
        }

        public static final class ValuesClause  {

            
            private final InsertStatement insertStatement;

            private List<${name}> listOf${name};

            ValuesClause(final List<${name}> theListOf${name},final InsertStatement insertStatement) {
                this.listOf${name} = theListOf${name};
                this.insertStatement = insertStatement;
            }

            public ValuesClause values(final ${name} the${name}) {
                this.listOf${name}.add(the${name});
                return this;
            }

            public int[] execute() throws SQLException  {
                int[] insertedRows = null;
                <@insertquery/>
                
                try (java.sql.Connection dbConnection = insertStatement.${name?uncap_first}Store.dbDataSource.getConnection();
                     PreparedStatement preparedStatement = dbConnection.prepareStatement(query))
                {
                    for (${name} ${name?uncap_first}:listOf${name}) {
                        this.insertStatement.prepare(preparedStatement, ${name?uncap_first});
                        preparedStatement.addBatch();
                    }
                    insertedRows = preparedStatement.executeBatch();
                }
                return insertedRows;
            }
            <#if table.hasPrimaryKey>
            public final List<${name}> returning() throws <@throwsblock/>  {
                List<${name}> insertedList = null ;
                <@insertquery isReturning=true/>

                try (java.sql.Connection dbConnection = insertStatement.${name?uncap_first}Store.dbDataSource.getConnection();
                     PreparedStatement preparedStatement = dbConnection.prepareStatement(query<#if table.hasAutoGeneratedPrimaryKey == true>, Statement.RETURN_GENERATED_KEYS</#if>))
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
                            insertedList.add(insertStatement.${name?uncap_first}Store.select(${getGeneratedPrimaryKeysFromRS()}).get()) ;
                          }
                          <#else>
                          if (insertedRows == 1) {
                            insertedList.add(insertStatement.${name?uncap_first}Store.select(${getPrimaryKeysFromModel("listOf${name}.get(i)")}).get());
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
            </#if>


        }
    }
	<#assign a=addImportStatement(beanPackage+"."+name)><#assign a=addImportStatement("java.sql.PreparedStatement")><#assign a=addImportStatement("java.sql.Statement")>
</#if>
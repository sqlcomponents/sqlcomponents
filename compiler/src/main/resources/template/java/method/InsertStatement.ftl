<#macro insertquery>
 <@compress single_line=true>INSERT INTO ${table.escapedName?j_string} (
  		<#assign index=0>
  		<#list insertableProperties as property>
  			<#if index == 0><#assign index=1><#else>,</#if>${property.column.escapedName?j_string}
  		</#list>
  		)
  	    VALUES <@insertqueryvalues/>
  		</@compress>
</#macro>

<#macro returning>
<#if (returningProperties?size > 0)>
          <#if orm.database.dbType == 'POSTGRES' && orm.database.databaseMajorVersion gt 10>
            returning <@columnSelection properties=returningProperties/>
          </#if>
  	    </#if>
</#macro>

<#macro insertqueryvalues>
(
  	    <#assign index=0>
  	    <#list insertableProperties as property>
  			<#if index == 0><#if sequenceName?? && table.highestPKIndex == 1>
  			<#list properties as property>
  			    <#if property.column.primaryKeyIndex == 1>nextval('${sequenceName}')</#if>
  			 </#list>
  			 <#else>    ${getPreparedValue(property,orm.insertMap)}</#if><#assign index=1><#else>            ,${getPreparedValue(property,orm.insertMap)}</#if>
  		</#list>
  	    )
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

        private int prepare(final PreparedStatement preparedStatement,final ${name} ${name?uncap_first}, int i) throws SQLException {
            <#assign index=0>
            <#assign column_index=1>
            <#list insertableProperties as property>
            <#if containsProperty(property,orm.insertMap)>
                <#if index == 0>
                <#if sequenceName?? && property.column.primaryKeyIndex == 1>
                <#else>
                ${property.name?uncap_first}(${name?uncap_first+"."+property.name + "()"}).set(preparedStatement,i++);
                <#assign column_index = column_index + 1>
                </#if>
                <#assign index=1>
                <#else>
                ${property.name?uncap_first}(${name?uncap_first+"."+property.name + "()"}).set(preparedStatement,i++);
                <#assign column_index = column_index + 1>
                </#if>
            </#if>
            </#list>
            return i;
        }

        private void prepare(final PreparedStatement preparedStatement,final List<${name}> ${name?uncap_first}s) throws SQLException {
            int startIndex = 1;
            for ( ${name} ${name?uncap_first} : ${name?uncap_first}s) {

            startIndex = prepare(preparedStatement, ${name?uncap_first}, startIndex);
            }
        }

        public ValueClause values(final ${name} ${name?uncap_first}) {
            return new ValueClause(${name?uncap_first},this);
        }

        public ValuesClause values(final ${name}... ${name?uncap_first}s) {
            return new ValuesClause(Arrays.asList(${name?uncap_first}s),this);
        }

        public ValuesClause values(final List<${name}> ${name?uncap_first}s) {
            return new ValuesClause(${name?uncap_first}s,this);
        }

        public static final class ValueClause  {
            
            private final InsertStatement insertStatement;

            private ${name} ${name?uncap_first};

            ValueClause(final ${name} ${name?uncap_first},final InsertStatement insertStatement) {
                this.${name?uncap_first} = ${name?uncap_first};
                this.insertStatement = insertStatement;
            }
            

            public int execute() throws SQLException  {
                int insertedRows = 0;

                final String query = "<@insertquery/>";

                

                try (java.sql.Connection dbConnection = insertStatement.${name?uncap_first}Store.dbDataSource.getConnection();
                     PreparedStatement preparedStatement = dbConnection.prepareStatement(query))
                {
                    this.insertStatement.prepare(preparedStatement,${name?uncap_first},1);
                    insertedRows = preparedStatement.executeUpdate();
                }
                return insertedRows;
            }

            <#if table.hasPrimaryKey>

            public ${name} returning() throws <@throwsblock/>  {
                ${name} inserted${name} = null ;

                
                final String query =  <@compress single_line=true>"<@insertquery/><@returning/>"</@compress>;

                try (java.sql.Connection dbConnection = insertStatement.${name?uncap_first}Store.dbDataSource.getConnection();
                     PreparedStatement preparedStatement = dbConnection.prepareStatement(query<#if table.hasAutoGeneratedPrimaryKey == true><#if orm.database.dbType == 'POSTGRES' && orm.database.databaseMajorVersion gt 10 ><#else>, Statement.RETURN_GENERATED_KEYS</#if></#if>))
                {
                    this.insertStatement.prepare(preparedStatement,${name?uncap_first},1);

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

            private List<${name}> ${name?uncap_first}s;

            ValuesClause (final List<${name}> ${name?uncap_first}s,final InsertStatement insertStatement) {
                this.${name?uncap_first}s = ${name?uncap_first}s;
                this.insertStatement = insertStatement;
            }



            public int execute() throws SQLException  {
                int insertedRows = 0;
                String query = "<@insertquery/>";

                if (${name?uncap_first}s.size() > 1) {
                    for (int i = 1; i < ${name?uncap_first}s.size() ; i++) {
                        query += <@compress single_line=true>" , <@insertqueryvalues/>"</@compress>;
                    }
                }

                try (java.sql.Connection dbConnection = insertStatement.${name?uncap_first}Store.dbDataSource.getConnection();
                     PreparedStatement preparedStatement = dbConnection.prepareStatement(query))
                {
                    this.insertStatement.prepare(preparedStatement,${name?uncap_first}s);
                    insertedRows = preparedStatement.executeUpdate();
                }
                return insertedRows;
            }

            <#if table.hasPrimaryKey>

            public List<${name}> returning() throws <@throwsblock/>  {
                List<${name}> inserted${name}s = new ArrayList<>() ;
                String query = "<@insertquery/>";

                if (${name?uncap_first}s.size() > 1) {
          for (int i = 1; i < ${name?uncap_first}s.size() ; i++) {
              query += <@compress single_line=true>" , <@insertqueryvalues/>"</@compress>;
          }
      }

      query += <@compress single_line=true>" <@returning/>"</@compress>;

                try (java.sql.Connection dbConnection = insertStatement.${name?uncap_first}Store.dbDataSource.getConnection();
                     PreparedStatement preparedStatement = dbConnection.prepareStatement(query<#if table.hasAutoGeneratedPrimaryKey == true><#if orm.database.dbType == 'POSTGRES' && orm.database.databaseMajorVersion gt 10 ><#else>, Statement.RETURN_GENERATED_KEYS</#if></#if>))
                {
                    this.insertStatement.prepare(preparedStatement,${name?uncap_first}s);

                    <#if orm.database.dbType == 'POSTGRES' && orm.database.databaseMajorVersion gt 10 >
                      <#if ((returningProperties?size) > 0)>
                                ResultSet resultSet = preparedStatement.executeQuery();
                                int j = 0;
                                while(resultSet.next()) {
                                    inserted${name}s.add(this.insertStatement.${name?uncap_first}Store.rowMapperForReturning(resultSet, ${name?uncap_first}s.get(j)));
                                    j++;
                                }
                      <#else>
                        if( preparedStatement.executeUpdate() == 1 ) {
                             inserted${name}s = ${name?uncap_first}s;
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
                return inserted${name}s;
            }
             </#if>
        }


    }
	<#assign a=addImportStatement(beanPackage+"."+name)><#assign a=addImportStatement("java.sql.PreparedStatement")><#assign a=addImportStatement("java.sql.Statement")><#assign a=addImportStatement("java.util.Arrays")>
</#if>
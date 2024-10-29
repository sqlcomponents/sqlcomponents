<#macro insertquery>
<@compress single_line=true>INSERT INTO ${table.escapedName?j_string} (
    <@columnSelection properties=insertableProperties/>

  		)
  	    VALUES <@insertqueryvalues/>
  		</@compress>
</#macro>

<#macro returning>
<#if (returningProperties?size > 0)>
          <#if orm.database.dbType == 'POSTGRES' && orm.database.databaseMajorVersion gt 11>
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
        return new InsertStatement(
        <#if containsEncryptedProperty() >
            this.encryptionFunction
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
        return new InsertStatement(
        
        <#if containsEncryptedProperty() >
            this.encryptionFunction
            ,this.decryptionFunction
        </#if>
        );
    }
    </#if>
    public final class InsertStatement {
    
        <#if containsEncryptedProperty() >
            private final Function<String,String> encryptionFunction;
            private final Function<String,String> decryptionFunction;
        </#if>

        private InsertStatement(
        <#if containsEncryptedProperty() >
            final Function<String,String> encryptionFunction
            ,final Function<String,String> decryptionFunction
        </#if>
                ) {


            <#if containsEncryptedProperty() >
            this.encryptionFunction = encryptionFunction;
            this.decryptionFunction = decryptionFunction;
            </#if>
        }

        private void prepare(final DataManager.SqlBuilder sqlBuilder,final ${name} ${name?uncap_first}) {
            <#assign index=0>
            <#assign column_index=1>
            <#list insertableProperties as property>
            <#if containsProperty(property,orm.insertMap)>
                <#if index == 0>
                <#if sequenceName?? && property.column.primaryKeyIndex == 1>
                <#else>
                ${property.name +"("+name?uncap_first+"."+property.name + "())"}.set(sqlBuilder);
                <#assign column_index = column_index + 1>
                </#if>
                <#assign index=1>
                <#else>
                ${property.name +"("+ name?uncap_first+"."+property.name + "())"}.set(sqlBuilder);
                <#assign column_index = column_index + 1>
                </#if>
            </#if>
            </#list>
        }

        private void prepare(final DataManager.SqlBuilder sqlBuilder,final List<${name}> ${name?uncap_first}s) {
            for ( ${name} ${name?uncap_first} : ${name?uncap_first}s) {
                prepare(sqlBuilder, ${name?uncap_first});
            }
        }

        public ValueClause values(final ${name} ${name?uncap_first}) {
            return new ValueClause(${name?uncap_first});
        }

        public ValuesClause values(final ${name}... ${name?uncap_first}s) {
            return new ValuesClause(Arrays.asList(${name?uncap_first}s));
        }

        public ValuesClause values(final List<${name}> ${name?uncap_first}s) {
            return new ValuesClause(${name?uncap_first}s);
        }

        public final class ValueClause  {

            private final ${name} ${name?uncap_first};

            ValueClause(final ${name} ${name?uncap_first}) {
                this.${name?uncap_first} = ${name?uncap_first};
            }
            

            public int execute(final DataSource dataSource) throws SQLException  {
                final String query = "<@insertquery/>";

                DataManager.SqlBuilder sqlBuilder = dataManager.sql(query);

                prepare(sqlBuilder,${name?uncap_first});

                return sqlBuilder.execute(dataSource);
            }

            <#if table.hasPrimaryKey>

            public ${name} returning(final DataSource dataSource) throws <@throwsblock/>  {

                ${name} inserted${name} = null;
                
                final String query =  <@compress single_line=true>"<@insertquery/><@returning/>"</@compress>;
                
                DataManager.SqlBuilder sqlBuilder = dataManager.sql(query);
                prepare(sqlBuilder,${name?uncap_first});
  
                    <#if orm.database.dbType == 'POSTGRES' && orm.database.databaseMajorVersion gt 11 >
                      <#if ((returningProperties?size) > 0)>
                      inserted${name} = sqlBuilder.queryForOne(resultSet -> rowMapperForReturning(resultSet,${name?uncap_first})).execute(dataSource);
        
                      <#else>
                        if( sqlBuilder.execute(dataSource) == 1 ) {
                             inserted${name} = ${name?uncap_first};
                        }
                      </#if>
                    <#else>
                    if( sqlBuilder.execute(dataSource) == 1 ) {
                                          <#if table.hasAutoGeneratedPrimaryKey == true>
                                          ResultSet res = preparedStatement.getGeneratedKeys();
                                          if (res.next()) {
                                              inserted${name} =  ${name}Store.this.select(dataSource,${getGeneratedPrimaryKeysFromRS()}).get();
                                          }
                                          <#else>
                                          inserted${name} =  ${name}Store.this.select(dataSource,${getPrimaryKeysFromModel(name?uncap_first)}).get();
                                          </#if>
                                      }
                    </#if>


                
                return inserted${name};
            }
             </#if>
        }

        public final class ValuesClause  {

            private final List<${name}> ${name?uncap_first}s;

            ValuesClause (final List<${name}> ${name?uncap_first}s) {
                this.${name?uncap_first}s = ${name?uncap_first}s;
            }

            public int execute(final DataSource dataSource) throws SQLException  {
                String query = "<@insertquery/>";

                if (${name?uncap_first}s.size() > 1) {
                    for (int i = 1; i < ${name?uncap_first}s.size() ; i++) {
                        query += <@compress single_line=true>" , <@insertqueryvalues/>"</@compress>;
                    }
                }

                DataManager.SqlBuilder sqlBuilder = dataManager.sql(query);
                prepare(sqlBuilder,${name?uncap_first}s);

                return sqlBuilder.execute(dataSource);

            }

            <#if table.hasPrimaryKey>

            public List<${name}> returning(final DataSource dataSource) throws <@throwsblock/>  {
                List<${name}> inserted${name}s = null;
                String query = "<@insertquery/>";

                if (${name?uncap_first}s.size() > 1) {
          for (int i = 1; i < ${name?uncap_first}s.size() ; i++) {
              query += <@compress single_line=true>" , <@insertqueryvalues/>"</@compress>;
          }
      }

      query += <@compress single_line=true>" <@returning/>"</@compress>;

                DataManager.SqlBuilder sqlBuilder = dataManager.sql(query);
                prepare(sqlBuilder,${name?uncap_first}s);



                    <#if orm.database.dbType == 'POSTGRES' && orm.database.databaseMajorVersion gt 11 >
                      <#if ((returningProperties?size) > 0)>

                            java.util.concurrent.atomic.AtomicInteger atomicInteger =  new java.util.concurrent.atomic.AtomicInteger(0);

                            inserted${name}s = sqlBuilder
              .queryForList(resultSet -> rowMapperForReturning(resultSet, ${name?uncap_first}s.get(atomicInteger.getAndIncrement()))).execute(dataSource); 

                                
                      <#else>
                        if( sqlBuilder.execute(dataSource) == 1 ) {
                             inserted${name}s = ${name?uncap_first}s;
                        }
                      </#if>
                    <#else>
                    if( sqlBuilder.execute(dataSource) == 1 ) {
                        inserted${name}s = new ArrayList<>(${name?uncap_first}s.size());
                                          <#if table.hasAutoGeneratedPrimaryKey == true>
                                          ResultSet res = preparedStatement.getGeneratedKeys();

                                          if (res.next()) {
                                              inserted${name}s.add(${name}Store.this.select(dataSource,${getGeneratedPrimaryKeysFromRS()}).get());
                                          }
                                          <#else>
                                          for(${name} ${name?uncap_first}:${name?uncap_first}s) {
                                          inserted${name}s.add(${name}Store.this.select(dataSource,${getPrimaryKeysFromModel(name?uncap_first)}).get());
                                          }
                                          </#if>
                                      }
                    </#if>


                
                return inserted${name}s;
            }
             </#if>
        }


    }
	<#assign a=addImportStatement(beanPackage+"."+name)><#assign a=addImportStatement("java.sql.PreparedStatement")><#assign a=addImportStatement("java.sql.Statement")><#assign a=addImportStatement("java.util.Arrays")>
</#if>
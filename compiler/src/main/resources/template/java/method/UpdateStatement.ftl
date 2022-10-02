
<#macro updatetquery>
  final String query = <@compress single_line=true>"
		UPDATE ${table.escapedName?j_string} SET
        		<#assign index=0>
        		<#list updatableProperties as property>
        			<#if property.column.primaryKeyIndex == 0>
        			<#if index == 0><#assign index=1><#else>,</#if>${property.column.escapedName?j_string} = ${getPreparedValue(property,orm.updateMap)}
        			</#if>
        		</#list>
        		WHERE
        	    <#assign index=0>
        		<#list properties as property>
        			<#if property.column.primaryKeyIndex != 0>
        			<#if index == 0><#assign index=1><#else> AND </#if>${property.column.escapedName?j_string} = ${getPreparedValue(property,orm.updateMap)}
        			</#if>
        		</#list>
		</@compress>";
</#macro>

<#if table.tableType == 'TABLE' >


public int update(${name} ${name?uncap_first}) throws SQLException {
        final String query = <@compress single_line=true>"
		UPDATE ${table.escapedName?j_string} SET
        		<#assign index=0>
        		<#list updatableProperties as property>
        			<#if property.column.primaryKeyIndex == 0>
        			<#if index == 0><#assign index=1><#else>,</#if>${property.column.escapedName?j_string} = ${getPreparedValue(property,orm.updateMap)}
        			</#if>
        		</#list>
        		WHERE
        	    <#assign index=0>
        		<#list properties as property>
        			<#if property.column.primaryKeyIndex != 0>
        			<#if index == 0><#assign index=1><#else> AND </#if>${property.column.escapedName?j_string} = ${getPreparedValue(property,orm.updateMap)}
        			</#if>
        		</#list>
		</@compress>";

		try (java.sql.Connection dbConnection = dbDataSource.getConnection();
             PreparedStatement preparedStatement = dbConnection.prepareStatement(query))
        {

        <#assign index=0>
        <#assign column_index=1>
        <#list updatableProperties as property>
        <#if orm.updateMap[property.column.columnName]??>
        <#else>
            <#if property.column.primaryKeyIndex == 0>
            <#if index == 0><#assign index=1><#else></#if>preparedStatement.set${getJDBCClassName(property.dataType)}(${column_index},${wrapSet(name?uncap_first+".get"+property.name?cap_first + "()",property)});
                                                          				<#assign column_index = column_index + 1>
            </#if>
        </#if>

        </#list>

        <#assign index=0>
        <#list properties as property>
            <#if property.column.primaryKeyIndex != 0>
            <#if index == 0><#assign index=1><#else></#if>preparedStatement.set${getJDBCClassName(property.dataType)}(${column_index},${wrapSet(name?uncap_first+".get"+property.name?cap_first + "()",property)});
                                                          				<#assign column_index = column_index + 1>
            </#if>
        </#list>

			return preparedStatement.executeUpdate();
        }
	}

	public final UpdateStatement update() {
        return new UpdateStatement(this
        <#list sampleDistinctCustomColumnTypeProperties as property>
                                                      ,this.convert${property.column.typeName?cap_first}
                                               </#list>);
    }

    public static final class UpdateStatement {
        private final ${name}Store ${name?uncap_first}Store;

        <#list sampleDistinctCustomColumnTypeProperties as property>
        private final ${orm.application.name}Manager.ConvertFunction<${getClassName(property.dataType)},Object> convert${property.column.typeName?cap_first};
        </#list>

        private UpdateStatement(final ${name}Store ${name?uncap_first}Store
        <#list sampleDistinctCustomColumnTypeProperties as property>
         ,final ${orm.application.name}Manager.ConvertFunction<${getClassName(property.dataType)},Object> theConvert${property.column.typeName?cap_first}
        </#list>
                ) {
            this.${name?uncap_first}Store = ${name?uncap_first}Store;
            <#list sampleDistinctCustomColumnTypeProperties as property>
            this.convert${property.column.typeName?cap_first} =  theConvert${property.column.typeName?cap_first};
            </#list>
        }

        private void prepare(final PreparedStatement preparedStatement,final ${name} ${name?uncap_first}) throws SQLException {
            <#assign index=0>
            <#assign column_index=1>
            <#list updatableProperties as property>
                <#if !orm.updateMap?keys?seq_contains(property.column.columnName)>
                    <#if property.column.primaryKeyIndex == 0>
                    <#if index == 0><#assign index=1><#else></#if>preparedStatement.set${getJDBCClassName(property.dataType)}(${column_index},${wrapSet(name?uncap_first+".get"+property.name?cap_first + "()",property)});
                                                                                <#assign column_index = column_index + 1>
                    </#if>
                </#if>
            </#list>

            <#assign index=0>
            <#list properties as property>
                <#if property.column.primaryKeyIndex != 0>
                <#if index == 0><#assign index=1><#else></#if>preparedStatement.set${getJDBCClassName(property.dataType)}(${column_index},${wrapSet(name?uncap_first+".get"+property.name?cap_first + "()",property)});
                                                                            <#assign column_index = column_index + 1>
                </#if>
            </#list>
        }


        public SetByPKClause set(final ${name} ${name?uncap_first}) {
            return new SetByPKClause(${name?uncap_first}Store.dbDataSource,${name?uncap_first},this);
        }

        public static final class SetByPKClause  {
                private final javax.sql.DataSource dbDataSource;
                private final UpdateStatement updateStatement;
                private ${name} ${name?uncap_first};

                SetByPKClause(final javax.sql.DataSource dbDataSource,final ${name} ${name?uncap_first},final UpdateStatement updateStatement) {
                    this.dbDataSource = dbDataSource;
                    this.${name?uncap_first} = ${name?uncap_first};
                    this.updateStatement = updateStatement;
                }

                public final int execute() throws SQLException  {
                    int updtedRows = 0;
                    <@updatetquery/>

                    try (java.sql.Connection dbConnection = dbDataSource.getConnection();
                        PreparedStatement preparedStatement = dbConnection.prepareStatement(query)){

                        this.updateStatement.prepare(preparedStatement,${name?uncap_first});

                        updtedRows = preparedStatement.executeUpdate();
                    }
                    return updtedRows;
                }

                <#if table.hasPrimaryKey>

                public final ${name} returning() throws SQLException  {
                    ${name} updated${name} = null ;
                    <@updatetquery/>

                    try (java.sql.Connection dbConnection = updateStatement.${name?uncap_first}Store.dbDataSource.getConnection();
                        PreparedStatement preparedStatement = dbConnection.prepareStatement(query<#if table.hasAutoGeneratedPrimaryKey == true>, Statement.RETURN_GENERATED_KEYS</#if>))
                    {
                        this.updateStatement.prepare(preparedStatement,${name?uncap_first});
                        if( preparedStatement.executeUpdate() == 1 ) {
                        <#if table.hasAutoGeneratedPrimaryKey == true>
                        ResultSet res = preparedStatement.getGeneratedKeys();
                        if (res.next()) {
                            updated${name} =  updateStatement.${name?uncap_first}Store.select(${getGeneratedPrimaryKeysFromRS()}).get();
                        }
                        <#else>
                        updated${name} =  updateStatement.${name?uncap_first}Store.select(${getPrimaryKeysFromModel(name?uncap_first)}).get();
                        </#if>
                    }
                    }
                    return updated${name};
                }
             </#if>
            }

        public SetClause set(final Value... values) {
            return new SetClause(values,this);
        }

        public static final class SetClause  {
            
            private final UpdateStatement updateStatement;

            private ${name} ${name?uncap_first};

            private Value[] values;

            SetClause(final Value[] values,final UpdateStatement updateStatement) {
                this.values = values;
                this.updateStatement = updateStatement;
            }

            SetClause(final ${name} ${name?uncap_first},final UpdateStatement updateStatement) {
                this.${name?uncap_first} = ${name?uncap_first};
                this.updateStatement = updateStatement;
            }

            public SetWhereClause where(WhereClause whereClause) {
                return new SetWhereClause(this);
            }

            

            public static final class SetWhereClause  {
                private final SetClause setClause;

                SetWhereClause(final SetClause setClause) {
                    this.setClause = setClause;
                }

                public final int execute() throws SQLException  {
                    return 0;
                }

                public final List<${name}> returning() throws SQLException  {
                    return null;
                }
            }


            

            
        }




    }	
</#if>




<#macro updatetquery>
  final String query = <@compress single_line=true>"
		UPDATE ${table.escapedName?j_string} SET
        		<#assign index=0>
        		<#list properties as property>
        			<#if property.column.primaryKeyIndex == 0>
        			<#if index == 0><#assign index=1><#else>,</#if>${property.column.escapedName?j_string} = ?
        			</#if>
        		</#list>
        		WHERE
        	    <#assign index=0>
        		<#list properties as property>
        			<#if property.column.primaryKeyIndex != 0>
        			<#if index == 0><#assign index=1><#else> AND </#if>${property.column.escapedName?j_string} = ?
        			</#if>
        		</#list>
		</@compress>";
</#macro>

<#if table.tableType == 'TABLE' >

	public final UpdateStatement update() {
        return new UpdateStatement(this
        <#list sampleDistinctCustomColumnTypeProperties as property>
                                                      ,this.convert${property.column.typeName?cap_first}
                                               </#list>);
    }

    public static final class UpdateStatement {
        private final ${name}Store${orm.daoSuffix} ${name?uncap_first}Store${orm.daoSuffix};

        <#list sampleDistinctCustomColumnTypeProperties as property>
        private final ${orm.application.name}Manager.ConvertFunction<${getClassName(property.dataType)},Object> convert${property.column.typeName?cap_first};
        </#list>

        private UpdateStatement(final ${name}Store${orm.daoSuffix} ${name?uncap_first}Store${orm.daoSuffix}
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
            <#list properties as property>
                <#if property.column.primaryKeyIndex == 0>
                <#if index == 0><#assign index=1><#else></#if>preparedStatement.set${getJDBCClassName(property.dataType)}(${column_index},${wrapSet(name?uncap_first,property)});
                                                                            <#assign column_index = column_index + 1>
                </#if>
            </#list>

            <#assign index=0>
            <#list properties as property>
                <#if property.column.primaryKeyIndex != 0>
                <#if index == 0><#assign index=1><#else></#if>preparedStatement.set${getJDBCClassName(property.dataType)}(${column_index},${wrapSet(name?uncap_first,property)});
                                                                            <#assign column_index = column_index + 1>
                </#if>
            </#list>
        }


        public SetByPKClause set(final ${name} ${name?uncap_first}) {
            return new SetByPKClause(${name?uncap_first}Store${orm.daoSuffix}.dataSource,${name?uncap_first},this);
        }

        public static final class SetByPKClause  {
                private final DataSource dataSource;
                private final UpdateStatement updateStatement;
                private ${name} ${name?uncap_first};

                SetByPKClause(final DataSource dataSource,final ${name} ${name?uncap_first},final UpdateStatement updateStatement) {
                    this.dataSource = dataSource;
                    this.${name?uncap_first} = ${name?uncap_first};
                    this.updateStatement = updateStatement;
                }

                public final int execute() throws SQLException  {
                    <@updatetquery/>

		try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query))
        {

            this.updateStatement.prepare(preparedStatement,${name?uncap_first});

			return preparedStatement.executeUpdate();
                }
                }

                public final ${name} returning() throws SQLException  {
                    return null;
                }
            }

        public SetClause set(final Value... values) {
            return new SetClause(null,this);
        }

        public static final class SetClause  {
            
            private final UpdateStatement updateStatement;

            private ${name} ${name?uncap_first};

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
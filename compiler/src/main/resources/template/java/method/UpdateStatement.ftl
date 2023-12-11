
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
		</@compress>"
+ ( this.whereClause == null ? "" : (" AND " + this.whereClause.asSql()) );
</#macro>

<#macro updatewithsetquery>
  final String query = <@compress single_line=true>"
		UPDATE ${table.escapedName?j_string} SET "+
                getSetValues()
		</@compress>
+ ( this.whereClause == null ? "" : (" WHERE " + this.whereClause.asSql()) );
</#macro>

<#if table.tableType == 'TABLE' >




	public final UpdateStatement update() {
        return new UpdateStatement(this

                                               
        <#if containsEncryptedProperty() >
            ,this.encryptionFunction
            ,this.decryptionFunction
        </#if>
        );
    }

    public static final class UpdateStatement {
        private final ${name}Store ${name?uncap_first}Store;


        <#if containsEncryptedProperty() >
            private final Function<String,String> encryptionFunction;
            private final Function<String,String> decryptionFunction;
        </#if>

        private UpdateStatement(final ${name}Store ${name?uncap_first}Store
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
            <#list updatableProperties as property>
                <#if containsProperty(property,orm.updateMap)>
                    <#if property.column.primaryKeyIndex == 0>
                    <#if index == 0><#assign index=1><#else></#if>${property.name?uncap_first}(${name?uncap_first+".get"+property.name?cap_first + "()"}).set(preparedStatement,${column_index});
                                                                                <#assign column_index = column_index + 1>
                    </#if>
                </#if>
            </#list>

            <#assign index=0>
            <#list properties as property>
                <#if property.column.primaryKeyIndex != 0>
                <#if index == 0><#assign index=1><#else></#if>${property.name?uncap_first}(${name?uncap_first+".get"+property.name?cap_first + "()"}).set(preparedStatement,${column_index});
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
                private WhereClause whereClause;
                private ${name} ${name?uncap_first};

                SetByPKClause(final javax.sql.DataSource dbDataSource,final ${name} ${name?uncap_first},final UpdateStatement updateStatement) {
                    this.dbDataSource = dbDataSource;
                    this.${name?uncap_first} = ${name?uncap_first};
                    this.updateStatement = updateStatement;
                }

                public final SetByPKClause where(final WhereClause whereClause) {
                    this.whereClause = whereClause;
                    return this;
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

                public final ${name} returning() throws <@throwsblock/>  {
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
                return new SetWhereClause(this, whereClause);
            } 

            public static final class SetWhereClause  {
                private final SetClause setClause;
                private WhereClause whereClause;

                SetWhereClause(final SetClause setClause, WhereClause whereClause) {
                    this.setClause = setClause;
                    this.whereClause = whereClause;
                }

                private String getSetValues() {
                    StringBuilder stringBuilder = new StringBuilder();
                    boolean isFirst = true;
                    for (Value value:
                            this.setClause.values) {
                        if(isFirst) {
                            isFirst = false;
                        } else {
                            stringBuilder.append(",");
                        }
                        stringBuilder.append(value.column().name()).append("=?");
                    }
                    return stringBuilder.toString();
                }
                
                public final int execute() throws SQLException  {
                    int updtedRows = 0;
                    <@updatewithsetquery/>

                    try (java.sql.Connection dbConnection = this.setClause.updateStatement.${name?uncap_first}Store.dbDataSource.getConnection();
                        PreparedStatement preparedStatement = dbConnection.prepareStatement(query)){

                        int index = 1;
                        for (Value value:
                                this.setClause.values) {
                            value.set(preparedStatement,index++);
                        }


                        updtedRows = preparedStatement.executeUpdate();
                    }
                    return updtedRows;
                }

                public final List<${name}> returning() throws <@throwsblock/>  {
                    return null;
                }
            }
            
        }
        
    public UpdateQuery sql(final String sql) {
        return new UpdateQuery(this, sql);
    }

    public static final class UpdateQuery  {

        private final UpdateStatement updateStatement;
        private final String sql;
        private final List<Value> values;

        public UpdateQuery(final UpdateStatement updateStatement, final String sql) {
            this.updateStatement = updateStatement;
            this.sql = sql;
            this.values = new ArrayList<>();
        }


        public UpdateQuery param(final Value value) {
            this.values.add(value);
            return this;
        }

        public int execute() throws SQLException {
            try (java.sql.Connection dbConnection = this.updateStatement.${name?uncap_first}Store.dbDataSource.getConnection();
                PreparedStatement preparedStatement = dbConnection.prepareStatement(sql)) {

                    int index = 1;
                for (Value value:values
                    ) {
                    value.set(preparedStatement, index++);
                }

                return preparedStatement.executeUpdate();
            }
        }


    }


    }	
</#if>



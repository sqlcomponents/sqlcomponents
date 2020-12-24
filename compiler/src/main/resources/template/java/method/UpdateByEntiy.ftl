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


        public SetClause set(final ${name} ${name?uncap_first}) {
            return new SetClause(${name?uncap_first},this);
        }

        public static final class SetClause  {
            
            private final UpdateStatement updateStatement;

            private ${name} ${name?uncap_first};

            SetClause(final ${name} ${name?uncap_first},final UpdateStatement updateStatement) {
                this.${name?uncap_first} = ${name?uncap_first};
                this.updateStatement = updateStatement;
            }

            public SetClause where(WhereClause whereClause) {
                return this;
            }

            public final int execute() throws SQLException  {
                return 0;
            }

            <#if table.hasPrimaryKey>
            public final ${name} returning() throws SQLException  {
                return null;
            }
             </#if>
        }




    }	
</#if>
<#if tableType == 'TABLE' >
<#include "/template/dao/java/method/signature/DeleteByPK.ftl"> {

		final String query = """
                DELETE FROM ${tableName} 
					WHERE
					<#assign index=0>
					<#list properties as property>
						<#if property.primaryKeyIndex != 0>		
						<#if index == 0><#assign index=1><#else>,</#if>${property.columnName} = ?
						</#if>
					</#list>
                """;

        try (Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query)) {
				 		
			${getPrimaryKeysAsPreparedStatements()}
            
            return preparedStatement.executeUpdate();
        }
	}<#assign a=addImportStatement("java.util.HashMap")>
</#if>
<#if highestPKIndex != 0>
<#include "/template/dao/java/method/signature/GetByPK.ftl"> {
		final String query = """
                SELECT
		<@columnSelection/> 
		FROM ${tableName} 
		WHERE
	    <#assign index=0>
		<#list properties as property>
			<#if property.primaryKeyIndex != 0>		
			<#if index == 0><#assign index=1><#else>AND </#if>${property.columnName} = ?
			</#if>
		</#list>
                """;
        try (Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            ${getPrimaryKeysAsPreparedStatements()}
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) return rowMapper(resultSet);


        } 

        return null;
	}<#assign a=addImportStatement("java.util.HashMap")>
</#if>
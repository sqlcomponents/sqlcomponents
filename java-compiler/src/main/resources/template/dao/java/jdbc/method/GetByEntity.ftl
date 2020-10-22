
public List<${name}> find(Criteria criteria) throws SQLException  {
String whereClause = criteria.asSql() ;
				final String query = """
                                SELECT
                		<@columnSelection/>
                		FROM ${tableName}
                                """+ (whereClause == null ? "" : (" WHERE " + whereClause));
                        try (Connection conn = dataSource.getConnection();
                            PreparedStatement preparedStatement = conn.prepareStatement(query)) {

                            ResultSet resultSet = preparedStatement.executeQuery();
                			List<${name}> arrays = new ArrayList();
                            while (resultSet.next()) {
                				arrays.add(rowMapper(resultSet));
                			}
                			return arrays;
                        }
	}
<#if orm.pagination >

<#include "/template/dao/java/method/signature/GetByEntity_Paginated.ftl"> {
		List<${name}> ${pluralName?uncap_first} = null ;
		HashMap<String, Object> map = new HashMap<String, Object>(3);
		map.put("${name?uncap_first}", search${name});
		if(pageNumber < 0 ) {
			throw new SQLException("Page Number " + pageNumber + "can not be less than 0");
		}
		else if(pageSize < 0 ) {
			throw new SQLException("Page size " + pageSize + "can not be less than 1");
		}
		else if (pageNumber == 1) {
			map.put("pageNumber", pageNumber);
			map.put("pageSize", pageSize);
			${pluralName?uncap_first} = sqlSession.selectList(
					"get${name}ByEntityPage1", map);
		}
		else {
			map.put("pageNumber", ( ( pageNumber - 1 ) * pageSize) + 1);
			map.put("pageSize", (( pageNumber - 1 ) * pageSize) + pageSize);
			${pluralName?uncap_first} = sqlSession.selectList("get${name}ByEntityPage",map) ;
		}		
				return null;
	}<#assign a=addImportStatement("java.util.HashMap")>
</#if>
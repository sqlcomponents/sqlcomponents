<#if table.tableType == 'MATERIALIZED_VIEW' >
    public void refresh() throws SQLException {
      final String query = "REFRESH MATERIALIZED VIEW ${table.escapedName?j_string}";
      dataManager.sql(query).execute(dataSource);
	}
</#if>

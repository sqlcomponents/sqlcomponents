<#if table.tableType == 'MATERIALIZED_VIEW' >
    public void refresh() throws SQLException {
                final String query = "REFRESH MATERIALIZED VIEW ${table.escapedName?j_string}";

                try (java.sql.Connection dbConnection = dbDataSource.getConnection();
                     PreparedStatement preparedStatement = dbConnection.prepareStatement(query))
                {
                   preparedStatement.executeUpdate();
                }
	}
</#if>

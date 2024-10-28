<#macro JsonNodeColumn property>
    <@columnheader property=property/>

    public void set(final DataManager.SqlBuilder preparedStatement, final  JsonNode jsonNode) {
    final String jsonText  = (jsonNode == null) ? null : jsonNode.toString() ;
    preparedStatement.param(jsonText, java.sql.Types.OTHER);
    }

    public JsonNode get(final ResultSet rs,final int index) throws SQLException {
        String jsonText = rs.getString(index);
        if (jsonText == null) {
            return null;
        }

        try {
            <#if orm.database.dbType == 'H2'>
                return new ObjectMapper().readTree(jsonText.substring(1,jsonText.length() - 1).replace("\\", ""));
            <#else>
                return new ObjectMapper().readTree(jsonText);
            </#if>
        } catch (JsonProcessingException e) {
            throw new SQLException(e);
        }
    }

    public final WhereClause  eq(final String value) {
    sql = "${property.column.escapedName?j_string} ='" + value + "'";
    return getWhereClause();
    }
    
    <@columnfooter property=property/>
</#macro>
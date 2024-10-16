<#macro JsonNodeColumn property>
    <@columnheader property=property/>

    public void set(final PreparedStatement preparedStatement, final int i, final  JsonNode jsonNode) throws SQLException {
    final String jsonText  = (jsonNode == null) ? null : jsonNode.toString() ;
    preparedStatement.setObject(i,jsonText, java.sql.Types.OTHER);
    }

    public JsonNode get(final ResultSet rs,final int index) throws SQLException {
        String jsonText = rs.getString(index);
        if (jsonText == null) {
            return null;
        }

        try {
            return new ObjectMapper().readTree(jsonText);
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
    public static final JsonNode getJsonb(final ResultSet rs,final int index) throws SQLException, JsonProcessingException {
        String jsonText = rs.getString(index);
        <#if orm.database.dbType == 'POSTGRES' >
        return jsonText == null ? null : new ObjectMapper().readTree(jsonText);
        <#elseif orm.database.dbType == 'H2'>
        return jsonText == null ? null : new ObjectMapper().readTree(jsonText.substring(1,jsonText.length() - 1).replace("\\", ""));
        </#if>
    }
    public static final String convertJsonb(final JsonNode jsonNode) throws SQLException {
        return (jsonNode == null) ? null : jsonNode.toString();
    }
    <#assign a=addImportStatement("com.fasterxml.jackson.databind.ObjectMapper")>
    <#assign a=addImportStatement("com.fasterxml.jackson.databind.JsonNode")>
    <#assign a=addImportStatement("com.fasterxml.jackson.core.JsonProcessingException")>
    <#assign a=addImportStatement("java.sql.ResultSet")>
    <#assign a=addImportStatement("java.sql.SQLException")>
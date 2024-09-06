 
    public static final String convertJsonb(final JsonNode jsonNode) throws SQLException {
        return (jsonNode == null) ? null : jsonNode.toString();
    }
    <#assign a=addImportStatement("com.fasterxml.jackson.databind.ObjectMapper")>
    <#assign a=addImportStatement("com.fasterxml.jackson.databind.JsonNode")>
    <#assign a=addImportStatement("com.fasterxml.jackson.core.JsonProcessingException")>
    <#assign a=addImportStatement("java.sql.ResultSet")>
    <#assign a=addImportStatement("java.sql.SQLException")>
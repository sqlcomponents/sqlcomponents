<#if orm.database.dbType == 'POSTGRES' >
    public static final JSONObject getJsonb(final ResultSet rs,final int index) throws SQLException {
        String jsonText = rs.getString(index);
        return jsonText == null ? null : new JSONObject(jsonText);
    }

    public static final String convertJsonb(final JSONObject jsonObject) throws SQLException {
        return (jsonObject == null) ? null : jsonObject.toString();
    }
    <#assign a=addImportStatement("org.json.JSONObject")>
    <#assign a=addImportStatement("java.sql.ResultSet")>
    <#assign a=addImportStatement("java.sql.SQLException")>
</#if>
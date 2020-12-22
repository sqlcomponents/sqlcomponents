<#if table.database.databaseProductName == 'PostgreSQL' >
    private JSONObject getJsonObject(final ResultSet rs,final int index) throws SQLException {
        PGobject pGobject = (PGobject) rs.getObject(index);
        return pGobject == null ? null : new JSONObject(pGobject.getValue());
    }

    private PGobject getJDBCObject(final JSONObject jsonObject) throws SQLException {
        if(jsonObject == null) {
            PGobject pGobject = new PGobject();
            pGobject.setType("json");
            pGobject.setValue(jsonObject.toString());
        }
        return null;
    }<#assign a=addImportStatement("org.postgresql.util.PGobject")><#assign a=addImportStatement("org.json.JSONObject")>
</#if>
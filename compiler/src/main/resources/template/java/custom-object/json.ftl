<#if orm.database.dbType == 'POSTGRES' >
    private final JSONObject getJson(final ResultSet rs,final int index) throws SQLException {
        PGobject pGobject = (PGobject) rs.getObject(index);
        return pGobject == null ? null : new JSONObject(pGobject.getValue());
    }

    private final PGobject convertJson(final JSONObject jsonObject) throws SQLException {
        if(jsonObject == null) {
            return null;
        }
        PGobject pGobject = new PGobject();
        pGobject.setType("json");
        pGobject.setValue(jsonObject.toString());
        return pGobject;
    }
    <#assign a=addImportStatement("org.postgresql.util.PGobject")>
    <#assign a=addImportStatement("org.json.JSONObject")>
    <#assign a=addImportStatement("java.sql.ResultSet")>
    <#assign a=addImportStatement("java.sql.SQLException")>
</#if>
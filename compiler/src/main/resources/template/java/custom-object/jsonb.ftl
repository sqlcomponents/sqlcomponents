<#if orm.database.dbType == 'POSTGRES' >
    private final JSONObject getJsonb(final ResultSet rs,final int index) throws SQLException {
        PGobject pGobject = (PGobject) rs.getObject(index);
        return pGobject == null ? null : new JSONObject(pGobject.getValue());
    }

    private final PGobject convertJsonb(final JSONObject jsonObject) throws SQLException {
        if(jsonObject == null) {
            PGobject pGobject = new PGobject();
            pGobject.setType("jsonb");
            pGobject.setValue(jsonObject.toString());
        }
        return null;
    }
    <#assign a=addImportStatement("org.postgresql.util.PGobject")>
    <#assign a=addImportStatement("org.json.JSONObject")>
    <#assign a=addImportStatement("java.sql.ResultSet")>
    <#assign a=addImportStatement("java.sql.SQLException")>
</#if>
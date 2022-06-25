<#if orm.database.dbType == 'POSTGRES' >
    private final UUID getUuid(final ResultSet rs,final int index) throws SQLException {
        return (UUID) rs.getObject(index);
    }

    private final PGobject convertUuid(final UUID uuid) throws SQLException {
        if(uuid == null) {
            PGobject pGobject = new PGobject();
            pGobject.setType("uuid");
            pGobject.setValue(uuid.toString());
        }
        return null;
    }
    <#assign a=addImportStatement("org.postgresql.util.PGobject")>
    <#assign a=addImportStatement("java.util.UUID")>
    <#assign a=addImportStatement("java.sql.ResultSet")>
    <#assign a=addImportStatement("java.sql.SQLException")>
</#if>
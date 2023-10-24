<#if orm.database.dbType == 'POSTGRES' >
    private final UUID getUuid(final ResultSet rs,final int index) throws SQLException {
        return (UUID) rs.getObject(index);
    }

    private final String convertUuid(final UUID uuid) throws SQLException {
        return (uuid == null) ? null : uuid.toString();
    }
    <#assign a=addImportStatement("org.postgresql.util.PGobject")>
    <#assign a=addImportStatement("java.util.UUID")>
    <#assign a=addImportStatement("java.sql.ResultSet")>
    <#assign a=addImportStatement("java.sql.SQLException")>
</#if>
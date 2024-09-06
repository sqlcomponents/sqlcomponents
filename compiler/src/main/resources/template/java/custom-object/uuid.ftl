<#if orm.database.dbType == 'POSTGRES' >

    public static final String convertUuid(final UUID uuid) throws SQLException {
        return (uuid == null) ? null : uuid.toString();
    }
    <#assign a=addImportStatement("java.util.UUID")>
    <#assign a=addImportStatement("java.sql.ResultSet")>
    <#assign a=addImportStatement("java.sql.SQLException")>
</#if>
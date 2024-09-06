<#if orm.database.dbType == 'POSTGRES' >

    public static final String convertVarbit(final BitSet uuid) throws SQLException {
        return (uuid == null) ? null : uuid.toString();
    }

    <#assign a=addImportStatement("java.util.BitSet")>
    <#assign a=addImportStatement("java.sql.ResultSet")>
    <#assign a=addImportStatement("java.sql.SQLException")>
</#if>
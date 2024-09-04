<#if orm.database.dbType == 'POSTGRES' >
    public static final BitSet getBit(final ResultSet rs,final int index) throws SQLException {
        String jsonText = rs.getString(index);
        return jsonText == null ? null : BitSet.valueOf(jsonText.getBytes());
    }

    public static final String convertBit(final BitSet uuid) throws SQLException {
        return (uuid == null) ? null : uuid.toString();
    }

    <#assign a=addImportStatement("java.util.BitSet")>
    <#assign a=addImportStatement("java.sql.ResultSet")>
    <#assign a=addImportStatement("java.sql.SQLException")>
</#if>
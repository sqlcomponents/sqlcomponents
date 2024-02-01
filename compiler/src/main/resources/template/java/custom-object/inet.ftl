<#if orm.database.dbType == 'POSTGRES' >
    public static final InetAddress getInet(final ResultSet rs,final int index) throws SQLException, UnknownHostException {
    String inetAddress = rs.getString(index);
    return inetAddress == null ? null : InetAddress.getByName(inetAddress);
    }
    public static final PGobject convertInet(final InetAddress inetAddress) throws SQLException {
    if(inetAddress == null) {
    return null;
    }
    PGobject pgObject = new PGobject();
    pgObject.setType("inet");
    pgObject.setValue(inetAddress.getHostAddress());
    return pgObject;
    }
    <#assign a=addImportStatement("java.net.InetAddress")>
    <#assign a=addImportStatement("java.net.UnknownHostException")>
    <#assign a=addImportStatement("java.sql.ResultSet")>
    <#assign a=addImportStatement("java.sql.SQLException")>
</#if>
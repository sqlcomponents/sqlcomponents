<#if orm.database.dbType == 'POSTGRES' >
    public static final SubnetUtils getCidr(final ResultSet rs,final int index) throws SQLException {
    String cidrAddress = rs.getString(index);
    return cidrAddress == null ? null : new SubnetUtils(cidrAddress);
    }

    public static final PGobject convertCidr(final SubnetUtils cidrAddress) throws SQLException {
    if(cidrAddress == null) {
    return null;
    }
    PGobject pgObject = new PGobject();
    pgObject.setType("cidr");
    pgObject.setValue(cidrAddress.getInfo().getCidrSignature());
    return pgObject;
    }
    <#assign a=addImportStatement("org.apache.commons.net.util.SubnetUtils")>
    <#assign a=addImportStatement("java.sql.ResultSet")>
    <#assign a=addImportStatement("java.sql.SQLException")>
</#if>
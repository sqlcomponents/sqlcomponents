<#if orm.database.dbType == 'POSTGRES' >

    public static final PGbox convertBox(final Envelope box) throws SQLException {
        return (box == null) ? null : new PGbox(box.getMinX(), box.getMinY(),
                                                                             box.getMaxX(), box.getMaxY());
    
    }
    
    <#assign a=addImportStatement("java.sql.ResultSet")>
    <#assign a=addImportStatement("java.sql.SQLException")>
    <#assign a=addImportStatement("org.locationtech.jts.geom.Envelope")>
    <#assign a=addImportStatement("org.postgresql.geometric.PGbox")>
</#if>
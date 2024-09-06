<#if orm.database.dbType == 'POSTGRES' >


    public static final PGpoint convertPoint(final Point point) throws SQLException {
        return (point == null) ? null : new PGpoint(point.getX(),point.getY());
    }
    <#assign a=addImportStatement("org.locationtech.jts.geom.GeometryFactory")>
    <#assign a=addImportStatement("org.locationtech.jts.geom.Coordinate")>
    <#assign a=addImportStatement("org.locationtech.jts.geom.Point")>
    <#assign a=addImportStatement("org.postgresql.geometric.PGpoint")>
    <#assign a=addImportStatement("java.sql.ResultSet")>
    <#assign a=addImportStatement("java.sql.SQLException")>
</#if>
<#if orm.database.dbType == 'POSTGRES' >
    public static final Point getPoint(final ResultSet rs,final int index) throws SQLException, JsonProcessingException {
        PGpoint pGpoint = (PGpoint) rs.getObject(index);
        return pGpoint == null ? null : new GeometryFactory().createPoint(new Coordinate(pGpoint.x,pGpoint.y));
    }

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
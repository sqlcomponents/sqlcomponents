<#if orm.database.dbType == 'POSTGRES' >
    public static final PGpolygon convertPolygon(final Polygon polygon) throws SQLException {
        if(polygon == null) {
            return null;
        }
        // Extract the coordinates from the exterior ring of the polygon
        Coordinate[] exteriorRingCoordinates = polygon.getExteriorRing().getCoordinates();
        // Convert the coordinates to PGpoint objects
        PGpoint[] pgPoints = new PGpoint[exteriorRingCoordinates.length];
        for (int i = 0; i < exteriorRingCoordinates.length; i++) {
            pgPoints[i] = new PGpoint(exteriorRingCoordinates[i].x, exteriorRingCoordinates[i].y);
        }
        return new PGpolygon(pgPoints);
    }
    <#assign a=addImportStatement("org.postgresql.geometric.PGpolygon")>
    <#assign a=addImportStatement("org.locationtech.jts.geom.Coordinate")>
    <#assign a=addImportStatement("org.locationtech.jts.geom.GeometryFactory")>
    <#assign a=addImportStatement("org.locationtech.jts.geom.LinearRing")>
    <#assign a=addImportStatement("org.locationtech.jts.geom.Polygon")>
    <#assign a=addImportStatement("org.locationtech.jts.io.ParseException")>
    <#assign a=addImportStatement("java.sql.ResultSet")>
    <#assign a=addImportStatement("java.sql.SQLException")>
</#if>
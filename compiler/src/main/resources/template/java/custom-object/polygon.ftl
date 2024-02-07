<#if orm.database.dbType == 'POSTGRES' >
    public static final Polygon getPolygon(final ResultSet rs,final int index) throws SQLException, JsonProcessingException {
        PGpolygon pgPolygon = (PGpolygon) rs.getObject(index);
          if(pgPolygon == null) {
            return null;
        }
        Coordinate[] coordinates = new Coordinate[pgPolygon.points.length + 1];
                for (int i = 0; i < pgPolygon.points.length; i++) {
                    org.postgresql.geometric.PGpoint point = pgPolygon.points[i];
                    coordinates[i] = new Coordinate(point.x, point.y);
                }
        // Close the ring by adding the first point at the end
        coordinates[pgPolygon.points.length] = coordinates[0];
        GeometryFactory factory = new GeometryFactory();
        LinearRing ring = factory.createLinearRing(coordinates);
        return factory.createPolygon(ring, null);
    }
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
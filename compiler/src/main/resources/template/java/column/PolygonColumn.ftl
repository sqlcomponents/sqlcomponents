<#macro PolygonColumn property>
    <@columnheader property=property/>
     public void set(final PreparedStatement preparedStatement, final int i, final Polygon polygon) throws SQLException {
    if(polygon == null) {
            preparedStatement.setObject(i,null);
        } else {
// Extract the coordinates from the exterior ring of the polygon
        Coordinate[] exteriorRingCoordinates = polygon.getExteriorRing().getCoordinates();
        // Convert the coordinates to PGpoint objects
        PGpoint[] pgPoints = new PGpoint[exteriorRingCoordinates.length];
        for (int j = 0; j < exteriorRingCoordinates.length; j++) {
            pgPoints[j] = new PGpoint(exteriorRingCoordinates[j].x, exteriorRingCoordinates[j].y);
        }
     preparedStatement.setObject(i,new PGpolygon(pgPoints));
        }
        
    }

    public Polygon get(final ResultSet rs,final int index) throws SQLException {
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
    <@columnfooter property=property/>
</#macro>
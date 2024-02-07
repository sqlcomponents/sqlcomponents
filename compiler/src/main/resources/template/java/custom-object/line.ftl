<#if orm.database.dbType == 'POSTGRES' >
    public static final LineString getLine(final ResultSet rs,final int index) throws SQLException, JsonProcessingException {
        PGline pGline = (PGline) rs.getObject(index);
        if(pGline == null) {
            return null;
        }
            double a = pGline.a;
            double b = pGline.b;
            double c = pGline.c;

            Coordinate startPoint = calculateStartPoint(a, b, c);
            Coordinate endPoint = calculateEndPoint(a, b, c);

            Coordinate[] coordinates = new Coordinate[]{
                    startPoint, endPoint
            };

            GeometryFactory geometryFactory = new GeometryFactory();
        return geometryFactory.createLineString(coordinates);
    }

    private static Coordinate calculateStartPoint(double a, double b, double c) {
        double startX = 0; // Define the x-coordinate for the start point
        double startY = (-c - a * startX) / b; // Calculate the corresponding y-coordinate
        return new Coordinate(startX, startY);
    }

    private static Coordinate calculateEndPoint(double a, double b, double c) {
        double endX = 1; // Define the x-coordinate for the end point
        double endY = (-c - a * endX) / b; // Calculate the corresponding y-coordinate
        return new Coordinate(endX, endY);
    }

    public static final PGline convertLine(final LineString line) throws SQLException {
        if(line == null) {
            return null;
        }
        PGpoint startPoint = new PGpoint(line.getCoordinateN(0).getX(),
                    line.getCoordinateN(0).getY());
        PGpoint endPoint = new PGpoint(line.getCoordinateN(1).getX(),
                    line.getCoordinateN(1).getY());
        return new PGline(startPoint,endPoint);
    }
    <#assign a=addImportStatement("org.locationtech.jts.geom.GeometryFactory")>
    <#assign a=addImportStatement("org.locationtech.jts.geom.Coordinate")>
    <#assign a=addImportStatement("org.locationtech.jts.geom.LineString")>
    <#assign a=addImportStatement("org.postgresql.geometric.PGpoint")>
    <#assign a=addImportStatement("org.postgresql.geometric.PGline")>
    <#assign a=addImportStatement("java.sql.ResultSet")>
    <#assign a=addImportStatement("java.sql.SQLException")>
</#if>








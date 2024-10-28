<#macro LineColumn property>
    <@columnheader property=property/>
     public void set(final DataManager.SqlBuilder preparedStatement, final LineString line) throws SQLException {
    if(line == null) {
            preparedStatement.paramNull();
        } else {
PGpoint startPoint = new PGpoint(line.getCoordinateN(0).getX(),
                    line.getCoordinateN(0).getY());
        PGpoint endPoint = new PGpoint(line.getCoordinateN(1).getX(),
                    line.getCoordinateN(1).getY());
     preparedStatement.param(new PGline(startPoint,endPoint));
        }
        
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
    
public LineString get(final ResultSet rs,final int index) throws SQLException {
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
    
    <@columnfooter property=property/>
</#macro>

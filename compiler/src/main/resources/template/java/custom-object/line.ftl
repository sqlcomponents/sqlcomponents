<#if orm.database.dbType == 'POSTGRES' >
   



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








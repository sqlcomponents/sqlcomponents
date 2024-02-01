<#if orm.database.dbType == 'POSTGRES' >
    public static final LineSegment getLseg(final ResultSet rs,final int index) throws SQLException, JsonProcessingException {
        PGlseg pGlseg = (PGlseg) rs.getObject(index);
        return pGlseg == null ? null : new LineSegment(pGlseg.point[0].x,pGlseg.point[0].y,pGlseg.point[1].x,pGlseg.point[1].y);
    }
    public static final PGlseg convertLseg(final LineSegment lineSegment) throws SQLException {
        if(lineSegment == null) {
            return null;
        }
        PGpoint pGpoint1 = new PGpoint(lineSegment.p0.x, lineSegment.p0.y);
        PGpoint pGpoint2 = new PGpoint(lineSegment.p1.x, lineSegment.p1.y);
        return new PGlseg(pGpoint1,pGpoint2);
    }
    <#assign a=addImportStatement("org.locationtech.jts.geom.GeometryFactory")>
    <#assign a=addImportStatement("org.locationtech.jts.geom.Coordinate")>
    <#assign a=addImportStatement("org.locationtech.jts.geom.LineSegment")>
    <#assign a=addImportStatement("org.postgresql.geometric.PGpoint")>
    <#assign a=addImportStatement("org.postgresql.geometric.PGlseg")>
    <#assign a=addImportStatement("java.sql.ResultSet")>
    <#assign a=addImportStatement("java.sql.SQLException")>
</#if>








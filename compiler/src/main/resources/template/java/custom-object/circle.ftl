<#if orm.database.dbType == 'POSTGRES' >
    public static final Circle getCircle(final ResultSet rs,final int index) throws SQLException, JsonProcessingException {

        PGcircle pGcircle = (PGcircle) rs.getObject(index);
          if(pGcircle == null) {
            return null;
        }
        PGpoint centerPoint = pGcircle.center;
        double radius = pGcircle.radius;
        return SpatialContext.GEO.makeCircle(centerPoint.x, centerPoint.y, radius);

    }

    public static final PGcircle convertCircle(final Circle circle) throws SQLException {

        if(circle == null) {
            return null;
        }

        PGpoint pGpoint = new PGpoint(circle.getCenter().getX(), circle.getCenter().getY());
        return new PGcircle(pGpoint, circle.getRadius());
    }

    <#assign a=addImportStatement("org.locationtech.spatial4j.shape.Circle")>
    <#assign a=addImportStatement("org.locationtech.spatial4j.context.SpatialContext")>
    <#assign a=addImportStatement("org.postgresql.geometric.PGpoint")>
    <#assign a=addImportStatement("org.postgresql.geometric.PGcircle")>
    <#assign a=addImportStatement("java.sql.ResultSet")>
    <#assign a=addImportStatement("java.sql.SQLException")>
</#if>
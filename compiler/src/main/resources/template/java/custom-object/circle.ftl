<#if orm.database.dbType == 'POSTGRES' >


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
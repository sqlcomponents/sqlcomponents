<#if orm.database.dbType == 'POSTGRES' >
    public static final Envelope getBox(final ResultSet rs,final int index) throws SQLException, JsonProcessingException {
        PGBox pGbox = (PGBox) rs.getObject(index);
        return pGbox == null ? null : new Envelope(pGbox.point[0].x,pGbox.point[1].x,pGbox.point[0].y,pGbox.point[1].y);
    }

    public static final PGbox convertBox(final Envelope box) throws SQLException {
        return (box == null) ? null : new PGbox(value.getMinX(), value.getMinY(),
                                                                             value.getMaxX(), value.getMaxY());
    
    }
    
    <#assign a=addImportStatement("java.sql.ResultSet")>
    <#assign a=addImportStatement("java.sql.SQLException")>
    <#assign a=addImportStatement("org.locationtech.jts.geom.Envelope")>
    <#assign a=addImportStatement("org.postgresql.geometric.PGbox")>
</#if>
<#macro PointColumn property>
    <@columnheader property=property/>

    public void set(final PreparedStatement preparedStatement, final int i, final Point point) throws SQLException {
    preparedStatement.setObject(i,(point == null) ? null : new PGpoint(point.getX(),point.getY()),java.sql.Types.OTHER);
    }

    public Point get(final ResultSet rs,final int index) throws SQLException {
        PGpoint pGpoint = (PGpoint) rs.getObject(index);
        return pGpoint == null ? null : new GeometryFactory().createPoint(new Coordinate(pGpoint.x,pGpoint.y));
    }



    <@columnfooter property=property/>
</#macro>
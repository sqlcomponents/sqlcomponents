<#macro BoxColumn property>
<@columnheader property=property/>
 
    public void set(final PreparedStatement preparedStatement, final int i, final Envelope box) throws SQLException {
    PGbox pgbox = (box == null) ? null : new PGbox(box.getMinX(), box.getMinY(),
                                                                             box.getMaxX(), box.getMaxY());
    preparedStatement.setObject(i,pgbox,java.sql.Types.OTHER);
    }

    public Envelope get(final ResultSet rs,final int index) throws SQLException {
        PGbox pGbox = (PGbox) rs.getObject(index);
        return pGbox == null ? null : new Envelope(pGbox.point[0].x,pGbox.point[1].x,pGbox.point[0].y,pGbox.point[1].y);
    }

    <@columnfooter property=property/>
</#macro>
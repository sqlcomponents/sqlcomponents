<#macro LineSegmentColumn property>
    <@columnheader property=property/>
    public void set(final PreparedStatement preparedStatement, final int i, final LineSegment lineSegment) throws SQLException {
    if(lineSegment == null) {
            preparedStatement.setObject(i,null);
        } else {
PGpoint pGpoint1 = new PGpoint(lineSegment.p0.x, lineSegment.p0.y);
        PGpoint pGpoint2 = new PGpoint(lineSegment.p1.x, lineSegment.p1.y);
  
    
    preparedStatement.setObject(i,new PGlseg(pGpoint1,pGpoint2));
        }
        
    }

    public LineSegment get(final ResultSet rs,final int index) throws SQLException {
        PGlseg pGlseg = (PGlseg) rs.getObject(index);
        return pGlseg == null ? null : new LineSegment(pGlseg.point[0].x,pGlseg.point[0].y,pGlseg.point[1].x,pGlseg.point[1].y);
    }
    
    <@columnfooter property=property/>
</#macro>
<#macro CircleColumn property>
    <@columnheader property=property/>
     public void set(final DataManager.SqlBuilder preparedStatement, final Circle circle) throws SQLException {
   if(circle == null) {
            preparedStatement.paramNull();
        } else {
PGpoint pGpoint = new PGpoint(circle.getCenter().getX(), circle.getCenter().getY());
     preparedStatement.param(new PGcircle(pGpoint, circle.getRadius()));
        }

        
    }

    public Circle get(final ResultSet rs,final int index) throws SQLException {

        PGcircle pGcircle = (PGcircle) rs.getObject(index);
          if(pGcircle == null) {
            return null;
        }
        PGpoint centerPoint = pGcircle.center;
        double radius = pGcircle.radius;
        return SpatialContext.GEO.makeCircle(centerPoint.x, centerPoint.y, radius);

    }


    <@columnfooter property=property/>
</#macro>
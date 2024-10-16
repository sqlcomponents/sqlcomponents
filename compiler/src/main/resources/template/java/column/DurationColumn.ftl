<#macro DurationColumn property>
    <@columnheader property=property/>
    public void set(final PreparedStatement preparedStatement, final int i, final Duration duration) throws SQLException {
    
    if(duration == null) {
        preparedStatement.setObject(i,null);
            
        } else {
            final int days = (int) duration.toDays();
            final int hours = (int) (duration.toHours() % 24);
            final int minutes = (int) (duration.toMinutes() % 60);
            final double seconds = duration.getSeconds() % 60;
            preparedStatement.setObject(i,new PGInterval(0, 0, days, hours, minutes, seconds));
        }
    
    
    }

    public Duration get(final ResultSet rs,final int index) throws SQLException {
        final PGInterval interval = (PGInterval) rs.getObject(index);

        if (interval == null) {
            return null;
        }

        final int days = interval.getDays();
        final int hours = interval.getHours();
        final int minutes = interval.getMinutes();
        final double seconds = interval.getSeconds();

        return Duration.ofDays(days)
                .plus(hours, ChronoUnit.HOURS)
                .plus(minutes, ChronoUnit.MINUTES)
                .plus((long) Math.floor(seconds), ChronoUnit.SECONDS);
    }

    public final WhereClause  eq(final String value) {
    sql = "${property.column.escapedName?j_string} ='" + value + "'";
    return getWhereClause();
    }

    

    <@columnfooter property=property/>
</#macro>
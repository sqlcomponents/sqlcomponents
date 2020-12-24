<#if orm.database.databaseType == 'POSTGRES' >
    private final Duration getInterval(final ResultSet rs,final int index) throws SQLException {
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

    private final PGobject convertInterval(final Duration duration) throws SQLException {
        if(duration != null) {
            final int days = (int) duration.toDays();
            final int hours = (int) (duration.toHours() % 24);
            final int minutes = (int) (duration.toMinutes() % 60);
            final double seconds = duration.getSeconds() % 60;
            return new PGInterval(0, 0, days, hours, minutes, seconds);
        }
        return null;
    }
    <#assign a=addImportStatement("org.postgresql.util.PGInterval")>
    <#assign a=addImportStatement("java.time.Duration")>
    <#assign a=addImportStatement("java.time.temporal.ChronoUnit")>
    <#assign a=addImportStatement("java.sql.ResultSet")>
    <#assign a=addImportStatement("java.sql.SQLException")>
</#if>
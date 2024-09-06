<#if orm.database.dbType == 'POSTGRES' >


    public static final PGobject convertInterval(final Duration duration) throws SQLException {
        if(duration != null) {
            final int days = (int) duration.toDays();
            final int hours = (int) (duration.toHours() % 24);
            final int minutes = (int) (duration.toMinutes() % 60);
            final double seconds = duration.getSeconds() % 60;
            return new PGInterval(0, 0, days, hours, minutes, seconds);
        }
        return null;
    }
    <#assign a=addImportStatement("org.postgresql.util.PGobject")>
    <#assign a=addImportStatement("org.postgresql.util.PGInterval")>
    <#assign a=addImportStatement("java.time.Duration")>
    <#assign a=addImportStatement("java.time.temporal.ChronoUnit")>
    <#assign a=addImportStatement("java.sql.ResultSet")>
    <#assign a=addImportStatement("java.sql.SQLException")>
</#if>
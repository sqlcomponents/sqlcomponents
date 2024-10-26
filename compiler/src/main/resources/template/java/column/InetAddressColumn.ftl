<#macro InetAddressColumn property>
    <@columnheader property=property/>
     public void set(final DataManager.SqlBuilder preparedStatement, final  InetAddress inetAddress) {
    if(inetAddress == null) {
    preparedStatement.paramNull();
    } else {
PGobject pgObject = new PGobject();
    pgObject.setType("inet");
    try {
            pgObject.setValue(inetAddress.getHostAddress());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
     preparedStatement.param(pgObject);
    }
    
    }
    public InetAddress get(final ResultSet rs,final int index) throws SQLException {
    String addressText = rs.getString(index);
    if(addressText == null) {
        return null;
    }
        try {
            return InetAddress.getByName(addressText);
        } catch (UnknownHostException e) {
            throw new SQLException(e);
        }
    }

    <@columnfooter property=property/>
</#macro>
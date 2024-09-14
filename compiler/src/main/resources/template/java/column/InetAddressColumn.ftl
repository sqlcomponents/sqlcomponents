<#macro InetAddressColumn property>
    <@columnheader property=property/>
     public void set(final PreparedStatement preparedStatement, final int i, final  InetAddress inetAddress) throws SQLException {
    if(inetAddress == null) {
    preparedStatement.setObject(i,null);
    } else {
PGobject pgObject = new PGobject();
    pgObject.setType("inet");
    pgObject.setValue(inetAddress.getHostAddress());
     preparedStatement.setObject(i,pgObject);
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